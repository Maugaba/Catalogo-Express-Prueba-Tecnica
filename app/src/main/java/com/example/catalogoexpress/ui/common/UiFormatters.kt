package com.example.catalogoexpress.ui.common

import java.text.NumberFormat
import java.util.Locale

private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-MX"))

fun Double.asCurrency(): String = currencyFormatter.format(this)

fun Double.asScore(): String = String.format(Locale.US, "%.4f", this)

fun Double.asRating(): String = String.format(Locale.US, "%.1f", this)
