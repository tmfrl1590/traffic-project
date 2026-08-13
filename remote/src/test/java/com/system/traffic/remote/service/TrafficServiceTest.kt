package com.system.traffic.remote.service

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.system.traffic.core.data.safeCall
import com.system.traffic.core.domain.DataError
import com.system.traffic.core.domain.Result
import com.system.traffic.data.model.remote.BusArriveEntity
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/*
* 주의: Json 설정은 NetworkModule에 있는 실제 설정과 똑같이 맞춰야 함
* 다르면 프로덕션에서 깨질 파싱이 테스트에선 통과하는(또는 반대) 어긋남이 생김
* NetworkModule의 Json 생성 부분을 함수로 빼서 양쪽에서 공유하면 제일 안전
* */

class TrafficServiceTest {

    private lateinit var server: MockWebServer
    private lateinit var service: TrafficService

    @Before
    fun setup() {
        server = MockWebServer()
        server.start()  // 임의의 로컬 포트에 가짜 서버 기동

        // 프로덕션과 같은 Json 설정 사용 (NetworkModule과 일치시킬 것!)
        val json = Json { ignoreUnknownKeys = true }

        service = Retrofit.Builder()
            .baseUrl(server.url("/"))  // BASE_URL 대신 가짜 서버 주소
            .addConverterFactory(json.asConverterFactory(contentType = "application/json".toMediaType()))
            .build()
            .create(TrafficService::class.java)
    }

    @After
    fun teardown() {
        server.shutdown()  // 포트 정리 (안 하면 테스트 간 누수)
    }

    @Test
    fun `버스도착 응답 JSON을 DTO로 파싱한다`() = runTest {
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(
                    """
                {
                  "RESPONSE": {
                    "ROW_COUNT": 3,
                    "ARRIVE_LIST": {
                      "ITEM": [
                        { "BUSSTOP_NAME": "광주역", "LINE_NAME": "수완03", "REMAIN_MIN": 5 },
                        { "BUSSTOP_NAME": "상무역", "LINE_NAME": "수완04", "REMAIN_MIN": 3 },
                        { "BUSSTOP_NAME": "유스퀘어", "LINE_NAME": "수완05", "REMAIN_MIN": 7 }
                      ]
                    }
                  }
                }
                """.trimIndent()
                )
        )

        val result = service.getBusArriveList(busStopId = "5001")
        val items = result.body()!!.response.arriveList!!.items

        assertEquals(3, items.size)
        assertEquals("광주역", items[0].busStopName)
        assertEquals("수완03", items[0].lineName)
        assertEquals("상무역", items[1].busStopName)
        assertEquals("수완04", items[1].lineName)
        assertEquals("유스퀘어", items[2].busStopName)
        assertEquals("수완05", items[2].lineName)
    }

    @Test
    fun `요청 URL에 정류장 id와 서비스키가 포함된다`() = runTest {
        server.enqueue(MockResponse().setResponseCode(200).setBody(VALID_RESPONSE))

        service.getBusArriveList(busStopId = "5001")

        val request = server.takeRequest()  // 서버가 받은 요청 꺼내기
        assertTrue(request.path!!.contains(other = "BUSSTOP_ID=5001"))
        assertTrue(request.path!!.contains(other = "serviceKey="))
    }

    @Test
    fun `429 응답이면 TOO_MANY_REQUESTS 에러가 된다`() = runTest {
        server.enqueue(MockResponse().setResponseCode(429))

        val result = safeCall<BusArriveEntity> {
            service.getBusArriveList(busStopId = "5001")
        }

        assertEquals(DataError.Remote.TOO_MANY_REQUESTS, (result as Result.Error).error)
    }

    @Test
    fun `서버에 연결할 수 없으면 NO_INTERNET 에러가 된다`() = runTest {
        server.shutdown()

        val result = safeCall<BusArriveEntity> {
            service.getBusArriveList(busStopId = "5001")
        }

        assertEquals(DataError.Remote.NO_INTERNET, (result as Result.Error).error)
    }

    @Test
    fun `서버가 응답하지 않으면 SERVER_TIMEOUT 에러가 된다`() = runTest {
        // 연결은 받되 응답을 영원히 주지 않는 소켓 정책
        server.enqueue(response = MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE))

        // 프로덕션의 30초 타임아웃을 그대로 쓰면 테스트가 30초 걸리므로 1초짜리 클라이언트 사용
        val timeoutService = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .client(
                OkHttpClient.Builder()
                    .readTimeout(1, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(
                Json { ignoreUnknownKeys = true }
                    .asConverterFactory(contentType = "application/json".toMediaType())
            )
            .build()
            .create(TrafficService::class.java)

        val result = safeCall<BusArriveEntity> {
            timeoutService.getBusArriveList(busStopId = "5001")
        }

        assertEquals(DataError.Remote.SERVER_TIMEOUT, (result as Result.Error).error)
    }

    companion object {
        // BusArriveEntity 기준 최소 유효 응답 (RESPONSE만 필수, 나머지는 기본값 처리)
        private val VALID_RESPONSE = """
        { "RESPONSE": {} }
    """.trimIndent()
    }
}