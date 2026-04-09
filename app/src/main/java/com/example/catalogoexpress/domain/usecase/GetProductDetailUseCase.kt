package com.example.catalogoexpress.domain.usecase

import com.example.catalogoexpress.data.repository.ProductRepository

class GetProductDetailUseCase(
    private val repository: ProductRepository,
) {
    suspend operator fun invoke(id: Long) = repository.getProduct(id)
}
