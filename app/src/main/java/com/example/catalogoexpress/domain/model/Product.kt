package com.example.catalogoexpress.domain.model

data class Product(
    val id: Long,
    val title: String,
    val description: String,
    val category: String,
    val brand: String,
    val type: String,
    val price: Double,
    val rating: Double,
    val stock: Int,
    val imageUrl: String,
    val isNew: Boolean,
    val status: String,
    val score: Double,
)
