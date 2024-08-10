package com.traffic.common


sealed class UIState<out T>(
    val data: T? = null,
){
    data object Idle: UIState<Nothing>()
    data object Loading: UIState<Nothing>()
    class Success<T>(data: T): UIState<T>(data)
    class Error<T>(data: T? = null): UIState<T>(data)

}
