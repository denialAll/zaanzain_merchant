package com.example.zaanzainmerchant.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zaanzainmerchant.api.AuthAPI
import com.example.zaanzainmerchant.database.AppDatabase
import com.example.zaanzainmerchant.repository.ProductListRepository
import com.example.zaanzainmerchant.utils.Category
import com.example.zaanzainmerchant.utils.Constants.TAG
import com.example.zaanzainmerchant.utils.ProductDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    authAPI: AuthAPI,
    application: Application
) : AndroidViewModel(application) {

    private val productListRepository = ProductListRepository(AppDatabase.getDatabase(application), authAPI)

    private val _productList = MutableStateFlow<List<ProductDetails>>(emptyList())
    val productList: StateFlow<List<ProductDetails>>
        get() = _productList

    private val _categoryList = MutableStateFlow<List<Category>>(emptyList())
    val categoryList: StateFlow<List<Category>>
        get() = _categoryList

    init {
        viewModelScope.launch {
            productListRepository.productList.collect{
                _productList.value = it
                _categoryList.value = it.map {
                    Category(it.category, it.categoryOrder)
                }
                    .distinctBy { it.category }
                    .sortedBy { it.categoryOrder }
            }
            for (cat in categoryList.value ){
                Log.d(TAG, "category: ${cat.category}, cat order: ${cat.categoryOrder}")
            }
        }
    }

    fun refreshProductList(){
        viewModelScope.launch {
            try {
                productListRepository.refreshProductList()
                Log.d(TAG, "successfully refreshed product list")
            } catch (e: Exception){
                Log.d(TAG, "refreshing product list failed! error is: $e")
            }
        }
    }
}
