package com.system.traffic.core.data

import com.system.traffic.core.domain.DataError
import com.system.traffic.core.domain.Result
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response
import java.net.SocketTimeoutException
import kotlin.coroutines.cancellation.CancellationException

class HttpClientExtTest {

    // ---- 성공 계열 ----

    @Test
    fun `성공 응답이면 body가 Success로 감싸진다`() {
        val response = Response.success("데이터")

        val result = responseToResult(response)

        assertEquals(Result.Success("데이터"), result)
    }

    @Test
    fun `성공인데 body가 null이면 SERIALIZATION 에러가 된다`() {
        val response = Response.success<String>(null)

        val result = responseToResult(response)

        assertEquals(Result.Error(DataError.Remote.SERIALIZATION), result)
    }

    // ---- 상태코드 매핑 (remote 테스트가 못 덮은 분기 포함) ----
    @Test
    fun `408이면 REQUEST_TIMEOUT`() {
        val response = Response.error<String>(408, "".toResponseBody())

        val result = responseToResult(response)

        assertEquals(Result.Error(DataError.Remote.REQUEST_TIMEOUT), result)
    }

    @Test
    fun `SocketTimeoutException이 나면 SERVER_TIMEOUT`() = runTest {
        val result = safeCall<String> { throw SocketTimeoutException() }

        assertEquals(Result.Error(DataError.Remote.SERVER_TIMEOUT), result)
    }

    @Test(expected = CancellationException::class)
    fun `CancellationException은 에러로 변환하지 않고 다시 던진다`() = runTest {
        safeCall<String> { throw CancellationException() }
    }
}