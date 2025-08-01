package com.system.traffic.core.data

import kotlinx.coroutines.ensureActive
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import com.system.traffic.core.domain.DataError
import com.system.traffic.core.domain.Result
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.coroutineContext

@OptIn(ExperimentalSerializationApi::class)
suspend inline fun <reified T> safeCall(
    execute: () -> Response<T>
): Result<T, DataError.Remote> {
    return try {
        val response = execute()
        responseToResult(response)
    } catch (e: IOException) {
        println("safeCall NO_INTERNET Error $e")
        Result.Error(DataError.Remote.NO_INTERNET)
    } catch (e: HttpException) {
        println("safeCall SERVER Error $e")
        Result.Error(DataError.Remote.SERVER)
    } catch (e: MissingFieldException) {
        println("safeCall SERIALIZATION Error $e")
        Result.Error(DataError.Remote.SERIALIZATION)
    } catch (e: Exception) {
        println("safeCall UNKNOWN Error $e")
        coroutineContext.ensureActive()
        Result.Error(DataError.Remote.UNKNOWN)
    }
}

inline fun <reified T> responseToResult(response: Response<T>): Result<T, DataError.Remote> {
    return when(response.code()) {
        in 200..299 -> {
            runCatching { response.body() }.getOrNull()?.let { body ->
                Result.Success(body)
            } ?: Result.Error(DataError.Remote.SERIALIZATION)
        }
        408 -> Result.Error(DataError.Remote.REQUEST_TIMEOUT)
        429 -> Result.Error(DataError.Remote.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.Remote.SERVER)
        else -> Result.Error(DataError.Remote.UNKNOWN)
    }
}