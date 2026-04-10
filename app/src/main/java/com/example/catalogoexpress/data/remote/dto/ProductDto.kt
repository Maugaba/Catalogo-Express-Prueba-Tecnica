package com.example.catalogoexpress.data.remote.dto

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("_id") val id: JsonElement? = null,
    val title: JsonElement? = null,
    val price: JsonElement? = null,
    val description: JsonElement? = null,
    val category: JsonElement? = null,
    val brand: JsonElement? = null,
    val type: JsonElement? = null,
    val stock: JsonElement? = null,
    val image: JsonElement? = null,
    val rating: JsonElement? = null,
    val isNew: JsonElement? = null,
)
