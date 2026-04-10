package com.example.catalogoexpress.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalogoexpress.core.result.AppResult
import com.example.catalogoexpress.core.result.UiState
import com.example.catalogoexpress.domain.model.Product
import com.example.catalogoexpress.domain.usecase.GetProductDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getProductDetailUseCase: GetProductDetailUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Product>>(UiState.Loading)
    val uiState: StateFlow<UiState<Product>> = _uiState.asStateFlow()

    fun loadProduct(id: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = when (val result = getProductDetailUseCase(id)) {
                is AppResult.Success -> UiState.Success(result.data)
                is AppResult.Error -> UiState.Error(result.error)
            }
        }
    }
}
