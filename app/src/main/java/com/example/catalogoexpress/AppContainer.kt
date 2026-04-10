package com.example.catalogoexpress

import android.app.Application
import androidx.room.Room
import com.example.catalogoexpress.data.local.CatalogoExpressDatabase
import com.example.catalogoexpress.data.remote.FakeStoreApi
import com.example.catalogoexpress.data.remote.ProductRemoteDataSource
import com.example.catalogoexpress.data.repository.DefaultProductRepository
import com.example.catalogoexpress.domain.usecase.GetProductDetailUseCase
import com.example.catalogoexpress.domain.usecase.GetProductsUseCase
import com.example.catalogoexpress.ui.common.AppViewModelFactory
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(
    application: Application,
) {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .callTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(FakeStoreApi::class.java)

    private val database = Room.databaseBuilder(
        application,
        CatalogoExpressDatabase::class.java,
        "catalogo-express.db",
    ).build()

    private val remoteDataSource = ProductRemoteDataSource(api)
    private val repository = DefaultProductRepository(remoteDataSource, database.productDao())
    private val getProductsUseCase = GetProductsUseCase(repository)
    private val getProductDetailUseCase = GetProductDetailUseCase(repository)

    val viewModelFactory = AppViewModelFactory(
        getProductsUseCase = getProductsUseCase,
        getProductDetailUseCase = getProductDetailUseCase,
    )
}
