package com.example.catalogoexpress.domain.usecase

import com.example.catalogoexpress.data.repository.ProductRepository

class GetProductsUseCase(
    private val repository: ProductRepository,
) {
    suspend operator fun invoke(forceRefresh: Boolean = false) = repository.getProducts(forceRefresh)
}
