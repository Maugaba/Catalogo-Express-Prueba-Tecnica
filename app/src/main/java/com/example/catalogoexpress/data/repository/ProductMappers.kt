package com.example.catalogoexpress.data.repository

import com.example.catalogoexpress.data.local.ProductEntity
import com.example.catalogoexpress.data.remote.dto.ProductDto
import com.example.catalogoexpress.domain.model.Product
import com.example.catalogoexpress.domain.usecase.ScoreCalculator
import com.google.gson.JsonElement

fun ProductDto.toDomain(): Product {
    val normalizedId = id.asLongOrDefault(0L)
    val normalizedTitle = title.asTrimmedString().ifEmpty { "Sin titulo" }
    val normalizedDescription = description.asTrimmedString().ifEmpty { "" }
    val normalizedCategory = category.asTrimmedString().ifEmpty { "Sin categoria" }
    val normalizedBrand = brand.asTrimmedString().ifEmpty { "Sin marca" }
    val normalizedType = type.asTrimmedString().ifEmpty { "Sin tipo" }
    val normalizedPrice = price.asDoubleOrDefault(0.0).coerceAtLeast(0.0)
    val normalizedRating = rating.asDoubleOrDefault(0.0).coerceIn(0.0, 5.0)
    val normalizedStock = stock.asIntOrDefault(0).coerceAtLeast(0)
    val normalizedImage = image.asTrimmedString()
    val normalizedIsNew = isNew.asBooleanOrDefault(false)
    val normalizedStatus = when {
        normalizedStock <= 0 -> "Sin stock"
        normalizedStock <= 10 -> "Pocas unidades"
        else -> "Disponible"
    }
    val normalizedScore = ScoreCalculator.calculate(
        rating = normalizedRating,
        stock = normalizedStock,
        price = normalizedPrice,
    )

    return Product(
        id = normalizedId,
        title = normalizedTitle,
        description = normalizedDescription,
        category = normalizedCategory,
        brand = normalizedBrand,
        type = normalizedType,
        price = normalizedPrice,
        rating = normalizedRating,
        stock = normalizedStock,
        imageUrl = normalizedImage,
        isNew = normalizedIsNew,
        status = normalizedStatus,
        score = normalizedScore,
    )
}

fun Product.toEntity(): ProductEntity = ProductEntity(
    id = id,
    title = title,
    description = description,
    category = category,
    brand = brand,
    type = type,
    price = price,
    rating = rating,
    stock = stock,
    imageUrl = imageUrl,
    isNew = isNew,
    status = status,
    score = score,
)

fun ProductEntity.toDomain(): Product = Product(
    id = id,
    title = title,
    description = description,
    category = category,
    brand = brand,
    type = type,
    price = price,
    rating = rating,
    stock = stock,
    imageUrl = imageUrl,
    isNew = isNew,
    status = status,
    score = score,
)

private fun JsonElement?.asTrimmedString(): String {
    if (this == null || isJsonNull) return ""
    val rawValue = when {
        isJsonPrimitive && asJsonPrimitive.isString -> asString
        isJsonPrimitive && asJsonPrimitive.isNumber -> asNumber.toString()
        isJsonPrimitive && asJsonPrimitive.isBoolean -> asBoolean.toString()
        else -> toString()
    }
    return rawValue.trim()
}

private fun JsonElement?.asDoubleOrDefault(defaultValue: Double): Double {
    return asTrimmedString().toDoubleOrNull() ?: defaultValue
}

private fun JsonElement?.asIntOrDefault(defaultValue: Int): Int {
    return asTrimmedString().toIntOrNull() ?: asTrimmedString().toDoubleOrNull()?.toInt() ?: defaultValue
}

private fun JsonElement?.asLongOrDefault(defaultValue: Long): Long {
    return asTrimmedString().toLongOrNull() ?: asTrimmedString().toDoubleOrNull()?.toLong() ?: defaultValue
}

private fun JsonElement?.asBooleanOrDefault(defaultValue: Boolean): Boolean {
    return when (asTrimmedString().lowercase()) {
        "true" -> true
        "false" -> false
        else -> defaultValue
    }
}
