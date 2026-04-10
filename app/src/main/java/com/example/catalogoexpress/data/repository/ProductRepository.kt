package com.example.catalogoexpress.data.repository

import com.example.catalogoexpress.core.result.AppResult
import com.example.catalogoexpress.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(forceRefresh: Boolean = false): AppResult<List<Product>>
    suspend fun getProduct(id: Long): AppResult<Product>
}
