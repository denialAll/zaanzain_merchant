package com.example.zaanzainmerchant.repository

import android.content.Context
import android.util.Log
import com.example.zaanzainmerchant.api.AuthAPI
import com.example.zaanzainmerchant.utils.Constants
import com.example.zaanzainmerchant.utils.ProductData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class AddProductRepository @Inject constructor(private val authAPI: AuthAPI) {

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun uploadProductData(productData: ProductData, context: Context) {

        val cacheDir = context.cacheDir
        val file = File(cacheDir, "product-image-${System.currentTimeMillis()}.png")
        var imagePart: MultipartBody.Part? = null

        if (productData.productPicture != null) {

            withContext(Dispatchers.IO) {

                val inputStream = context.contentResolver.openInputStream(productData.productPicture)
                val outputStream = FileOutputStream(file)
                inputStream!!.copyTo(outputStream)
                inputStream.close()

            }

            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            imagePart = MultipartBody.Part.createFormData("product_picture", file.name, requestBody)
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response =
                    authAPI.postProductData(
                        image = imagePart,
                        title = productData.title,
                        description = productData.description,
                        price = productData.price,
                        category = productData.category,
                        categoryOrder = productData.categoryOrder,
                        isAvailable = productData.isAvailable,
                        servings = productData.servings
                    )
                Log.d(Constants.TAG, "success! ${response.string()}")
            } catch (e: Exception) {
                Log.d(Constants.TAG, "fiasco ${e.message}")
            }
        }
    }
}