package com.example.catalogoexpress.data.repository

import com.example.catalogoexpress.core.network.toAppError
import com.example.catalogoexpress.core.result.AppError
import com.example.catalogoexpress.core.result.AppResult
import com.example.catalogoexpress.data.local.ProductDao
import com.example.catalogoexpress.data.remote.ProductRemoteDataSource
import com.example.catalogoexpress.domain.model.Product

class DefaultProductRepository(
    private val remoteDataSource: ProductRemoteDataSource,
    private val productDao: ProductDao,
) : ProductRepository {

    override suspend fun getProducts(forceRefresh: Boolean): AppResult<List<Product>> {
        return try {
            val remoteProducts = remoteDataSource.getProducts()
                .map { it.toDomain() }
                .sortedByDescending { it.score }

            productDao.upsertAll(remoteProducts.map(Product::toEntity))
            AppResult.Success(remoteProducts)
        } catch (throwable: Throwable) {
            val cachedProducts = productDao.getProducts().map { it.toDomain() }
            if (cachedProducts.isNotEmpty()) {
                AppResult.Success(cachedProducts.sortedByDescending { it.score })
            } else {
                AppResult.Error(throwable.toAppError())
            }
        }
    }

    override suspend fun getProduct(id: Long): AppResult<Product> {
        productDao.getProductById(id)?.let { entity ->
            return AppResult.Success(entity.toDomain())
        }

        return when (val productsResult = getProducts(forceRefresh = true)) {
            is AppResult.Success -> {
                productsResult.data.firstOrNull { it.id == id }
                    ?.let { product -> AppResult.Success(product) }
                    ?: AppResult.Error(AppError.NotFound)
            }

            is AppResult.Error -> productsResult
        }
    }
}
