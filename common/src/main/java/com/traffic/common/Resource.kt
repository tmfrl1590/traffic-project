package com.traffic.common

sealed class Resource<T>{
    class Idle<T>:Resource<T>()
    class Loading<T>:Resource<T>()
    class Success<T>(val data:T):Resource<T>()
    class Error<T>(val data:T? = null):Resource<T>()
}