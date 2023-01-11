package com.example.zaanzainmerchant.repository

import android.content.Context
import android.net.Uri
import com.example.zaanzainmerchant.api.AuthAPI
import com.example.zaanzainmerchant.database.AppDatabase
import com.example.zaanzainmerchant.utils.*
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

class ProductEditRepository @Inject constructor(
    private val authAPI: AuthAPI
    ) {

    suspend fun updateProductData(productId: Int, productData: ProductUpdateData){

        authAPI.updateProductData(
            id = productId,
            productData = productData
        )
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun updateProductImage(productId: Int, productImage: Uri, context: Context) {

        val cacheDir = context.cacheDir
        val file = File(cacheDir, "product-image-${System.currentTimeMillis()}.png")

        withContext(Dispatchers.IO) {

            val inputStream = context.contentResolver.openInputStream(productImage)
            val outputStream = FileOutputStream(file)
            inputStream!!.copyTo(outputStream)
            inputStream.close()

        }
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("product_picture", file.name, requestBody)

        CoroutineScope(Dispatchers.IO).launch {
            authAPI.updateProductImage(
                id = productId,
                image = imagePart
            )
        }
    }

}