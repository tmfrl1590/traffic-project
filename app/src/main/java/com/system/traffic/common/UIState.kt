package com.system.traffic.common


sealed class UIState<T>(
    val data: T? = null,
){
    class Success<T>(data: T): UIState<T>(data)
    class Error<T>(data: T? = null): UIState<T>(data)
    class Loading<T>: UIState<T>()
}
