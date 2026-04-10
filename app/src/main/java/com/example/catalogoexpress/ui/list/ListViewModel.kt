package com.example.catalogoexpress.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalogoexpress.core.result.AppResult
import com.example.catalogoexpress.core.result.UiState
import com.example.catalogoexpress.domain.model.Product
import com.example.catalogoexpress.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListViewModel(
    private val getProductsUseCase: GetProductsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Product>>> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts(forceRefresh: Boolean = true) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = when (val result = getProductsUseCase(forceRefresh)) {
                is AppResult.Success -> UiState.Success(result.data)
                is AppResult.Error -> UiState.Error(result.error)
            }
        }
    }
}
