package com.example.myapplication11.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.myapplication11.models.Product
import com.example.myapplication11.network.ProductApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface ProductUiState {
    data class Success(val produits: List<Product>) : ProductUiState
    object Error : ProductUiState
    object Loading : ProductUiState
}

class ProductViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState

    fun fetchProducts() {
        viewModelScope.launch {
            _uiState.value = try {
                val produits = ProductApi.retrofitService.getAllProducts()
                ProductUiState.Success(produits)
            } catch (e: Exception) {
                ProductUiState.Error
            }
        }
    }
}
