package com.example.catalogoexpress.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catalogoexpress.domain.usecase.GetProductDetailUseCase
import com.example.catalogoexpress.domain.usecase.GetProductsUseCase
import com.example.catalogoexpress.ui.detail.DetailViewModel
import com.example.catalogoexpress.ui.list.ListViewModel

class AppViewModelFactory(
    private val getProductsUseCase: GetProductsUseCase,
    private val getProductDetailUseCase: GetProductDetailUseCase,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ListViewModel::class.java) -> {
                ListViewModel(getProductsUseCase) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(getProductDetailUseCase) as T
            }

            else -> error("ViewModel no soportado: ${modelClass.simpleName}")
        }
    }
}
