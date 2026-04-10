package com.example.catalogoexpress.core.result

sealed interface AppError {
    data object Offline : AppError
    data object Timeout : AppError
    data class Backend(val code: Int, val message: String?) : AppError
    data object NotFound : AppError
    data class Unknown(val cause: Throwable? = null) : AppError
}
