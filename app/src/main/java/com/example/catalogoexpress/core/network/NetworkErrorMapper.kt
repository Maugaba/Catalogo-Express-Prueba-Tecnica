package com.example.catalogoexpress.core.network

import com.example.catalogoexpress.core.result.AppError
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import retrofit2.HttpException

fun Throwable.toAppError(): AppError = when (this) {
    is SocketTimeoutException -> AppError.Timeout
    is UnknownHostException -> AppError.Offline
    is IOException -> AppError.Offline
    is HttpException -> AppError.Backend(code(), message())
    else -> AppError.Unknown(this)
}
