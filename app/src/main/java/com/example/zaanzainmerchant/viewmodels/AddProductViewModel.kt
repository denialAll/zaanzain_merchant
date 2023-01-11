package com.example.zaanzainmerchant.viewmodels

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.zaanzainmerchant.repository.AddProductRepository
import com.example.zaanzainmerchant.utils.Constants
import com.example.zaanzainmerchant.utils.Constants.TAG
import com.example.zaanzainmerchant.utils.ProductData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val addProductRepository: AddProductRepository,
    application: Application) :
    AndroidViewModel(application) {

    var title by mutableStateOf("")
        private set

    fun updateTitle(productTitle: String) {
        title = productTitle
    }

    var category by mutableStateOf("")
        private set

    fun updateCategory(productCategory: String) {
        category = productCategory
    }

    var categoryOrder by mutableStateOf("")
        private set

    fun updateCategoryOrder(productCategoryOrder: String) {
        categoryOrder = productCategoryOrder
    }

    var description by mutableStateOf("")
        private set

    fun updateDescription(productDescription: String) {
        description = productDescription
    }

    var price by mutableStateOf("")
        private set

    fun updatePrice(productPrice: String) {
        price = productPrice
    }

    var servings by mutableStateOf("")
        private set

    fun updateServings(productServings: String) {
        servings = productServings
    }

    var isAvailable by mutableStateOf(false)
        private set

    fun updateIsProductAvailable(isProductAvailable: Boolean) {
        isAvailable = isProductAvailable
    }

    var imageUri by mutableStateOf<Uri?>(null)
        private set

    fun updateImageUri(uri: Uri) {
        imageUri = uri
    }

    fun uploadProductData(
        uri: Uri? = imageUri,
        context: Context = getApplication<Application>().applicationContext
    ) {
        val data = ProductData(
            title = title,
            description = description,
            price = price.toDouble(),
            category = category,
            categoryOrder = categoryOrder.toInt(),
            servings = servings.toInt(),
            isAvailable = isAvailable,
            productPicture = uri
        )
        viewModelScope.launch {
            try {
                Log.d(TAG, "inside viewmodel scope")
                addProductRepository.uploadProductData(
                    productData = data,
                    context = context
                )
            } catch (e: Exception) {
                Log.d(Constants.TAG, "failed ${e.message}")
            }
        }
    }

}