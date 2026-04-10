package com.example.catalogoexpress.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: Long,
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
