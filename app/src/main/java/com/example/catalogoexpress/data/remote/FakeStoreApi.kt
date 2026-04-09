package com.example.catalogoexpress.data.remote

import com.example.catalogoexpress.data.remote.dto.ProductsResponseDto
import retrofit2.http.GET
import retrofit2.http.Url

interface FakeStoreApi {
    @GET
    suspend fun getProducts(@Url path: String): ProductsResponseDto
}
