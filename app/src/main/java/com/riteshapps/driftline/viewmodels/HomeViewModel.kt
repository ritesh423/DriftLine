package com.riteshapps.driftline.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.riteshapps.driftline.models.Product
import com.riteshapps.driftline.utils.ProductRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {


    private val repository = ProductRepository()
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private var allProducts: List<Product> = emptyList()

    fun fetchProducts() {
        viewModelScope.launch {
            try {
                val productList = repository.getAllProducts()
                allProducts = productList
                _products.postValue(productList)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error: ${e.message}")
            }
        }
    }

    fun searchProducts(query: String) {
        viewModelScope.launch {
            val filtered = allProducts.filter { product ->
                product.title.contains(query, ignoreCase = true)
            }
            _products.postValue(filtered)
        }
    }

    fun resetSearch() {
        fetchProducts()
    }
}
