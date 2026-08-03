package com.system.traffic.core.data

import com.system.traffic.core.domain.DataError
import com.system.traffic.core.domain.Result
import kotlinx.serialization.SerializationException
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.coroutines.cancellation.CancellationException

val logger: Logger = Logger.getLogger("HttpClient")

suspend inline fun <reified T> safeCall(
    execute: suspend () -> Response<T>
): Result<T, DataError.Remote> {
    return try {
        val response = execute()
        responseToResult(response)
    } catch (e: CancellationException) { // 코루틴 취소는 정상이므로 로그를 남기지 않고 다시 던짐, 취소는 정상 신호, 최우선 재전파
        throw e
    } catch (e: IOException) {
        logger.log(Level.WARNING, "safeCall NO_INTERNET Error", e)
        Result.Error(DataError.Remote.NO_INTERNET)
    } catch (e: SerializationException) {
        logger.log(Level.WARNING, "safeCall SERIALIZATION Error", e)
        Result.Error(DataError.Remote.SERIALIZATION)
    } catch (e: HttpException) {
        logger.log(Level.WARNING, "safeCall SERVER Error", e)
        Result.Error(DataError.Remote.SERVER)
    }  catch (e: Exception) {
        logger.log(Level.WARNING, "safeCall UNKNOWN Error", e)
        Result.Error(DataError.Remote.UNKNOWN)
    }
}

inline fun <reified T> responseToResult(response: Response<T>): Result<T, DataError.Remote> {
    if (response.isSuccessful) {
        val body = response.body()
        return when {
            body != null -> Result.Success(data = body)
            T::class == Unit::class -> Result.Success(data = Unit as T)
            else -> {
                logger.warning("safeCall: ${response.code()} success but body is null for non-Unit type")
                Result.Error(DataError.Remote.SERIALIZATION)
            }
        }
    }

    // 실패 응답: 분기 전에 딱 한 번 로깅 (매핑과 로깅의 분리)
    val code = response.code()
    val errorBody = response.errorBody()?.string()?.take(n = 500)   // 대용량 HTML 방어
    logger.warning("safeCall HTTP $code error, body=$errorBody")

    return when (code) {
        408 -> Result.Error(DataError.Remote.REQUEST_TIMEOUT)
        429 -> Result.Error(DataError.Remote.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.Remote.SERVER)
        else -> Result.Error(DataError.Remote.UNKNOWN)
    }
}