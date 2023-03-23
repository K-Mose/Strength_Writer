package com.example.strengthwriter.utils

sealed class RequestState<out T> {
    object Idle : RequestState<Nothing>()
    data class Loading<T>(val data: T) : RequestState<T>()
    data class Success<T>(val data: T): RequestState<T>()
    data class Error(val error: Throwable): RequestState<Nothing>()
}