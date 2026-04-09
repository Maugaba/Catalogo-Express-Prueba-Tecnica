package com.example.catalogoexpress.data.remote

import com.example.catalogoexpress.BuildConfig
import com.example.catalogoexpress.data.remote.dto.ProductDto

class ProductRemoteDataSource(
    private val api: FakeStoreApi,
) {
    suspend fun getProducts(): List<ProductDto> = api.getProducts(BuildConfig.PRODUCTS_PATH).data
}
