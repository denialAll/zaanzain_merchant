package com.example.zaanzainmerchant.viewmodels

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.zaanzainmerchant.repository.AddProductRepository
import com.example.zaanzainmerchant.repository.ProductEditRepository
import com.example.zaanzainmerchant.utils.Constants.TAG
import com.example.zaanzainmerchant.utils.ProductDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.internal.platform.Jdk9Platform.Companion.isAvailable
import javax.inject.Inject

@HiltViewModel
class ProductEditViewModel @Inject constructor(
    application: Application
) :
    AndroidViewModel(application) {


    private val _productToEdit  = MutableStateFlow<ProductDetails?>(null)
    val productToEdit: StateFlow<ProductDetails?>
        get() = _productToEdit

    fun updateProductIdToEdit(product: ProductDetails){
        _productToEdit.value = product
    }

    init {
        viewModelScope.launch {
            productToEdit.collect {
                if (it != null) {
                    _title.value = it.title
                    _category.value = it.category
                    _categoryOrder.value = it.categoryOrder.toString()
                    _description.value = it.description
                    _price.value = it.price.toString()
                    _servings.value = it.servings.toString()
                    _imageUri.value = it.productPicture
                }

            }

        }
    }

    private val _title = MutableStateFlow<String?>("")
    val title: StateFlow<String?>
        get() = _title

    fun updateTitle(productTitle: String) {
        _title.value = productTitle
    }

    private val _category = MutableStateFlow<String?>("")
    val category: StateFlow<String?>
        get() = _category

    fun updateCategory(productCategory: String) {
        _category.value = productCategory
    }

    private val _categoryOrder = MutableStateFlow<String?>("")
    val categoryOrder: StateFlow<String?>
        get() = _categoryOrder

    fun updateCategoryOrder(productCategoryOrder: String) {
        _categoryOrder.value = productCategoryOrder
    }

    private val _description = MutableStateFlow<String?>("")
    val description: StateFlow<String?>
        get() = _description

    fun updateDescription(productDescription: String) {
        _description.value = productDescription
    }

    private val _price = MutableStateFlow<String?>("0.0")
    val price: StateFlow<String?>
        get() = _price

    fun updatePrice(productPrice: String) {
        _price.value = productPrice
    }

    private val _servings = MutableStateFlow<String?>("1")
    val servings: StateFlow<String?>
        get() = _servings

    fun updateServings(productServings: String) {
        _servings.value = productServings
    }

    private val _isProductProductAvailable = MutableStateFlow<Boolean?>(false)
    val isProductAvailable: StateFlow<Boolean?>
        get() = _isProductProductAvailable

    fun updateIsProductAvailable(isAvailable: Boolean) {
        _isProductProductAvailable.value = isAvailable
    }

    private val _imageUri = MutableStateFlow<String?>("")
    val imageUri: StateFlow<String?>
        get() = _imageUri

    fun updateImageUri(uri: Uri) {
        _imageUri.value = uri.toString()
    }
}