package com.example.catalogoexpress.ui.common

import android.content.Context
import com.example.catalogoexpress.R
import com.example.catalogoexpress.core.result.AppError

fun AppError.toUiMessage(context: Context): String = when (this) {
    AppError.Offline -> context.getString(R.string.error_offline)
    AppError.Timeout -> context.getString(R.string.error_timeout)
    is AppError.Backend -> context.getString(R.string.error_backend) + " (${code})"
    AppError.NotFound -> context.getString(R.string.error_not_found)
    is AppError.Unknown -> context.getString(R.string.error_generic)
}
