package com.example.zaanzainmerchant.repository

import android.content.Context
import android.util.Log
import com.example.zaanzainmerchant.api.AuthAPI
import com.example.zaanzainmerchant.utils.Constants.TAG
import com.example.zaanzainmerchant.utils.MerchantData
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

class RestaurantDetailRepository @Inject constructor(private val authAPI: AuthAPI) {


    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun uploadMerchantData(merchantData: MerchantData, context: Context) {

        val filesDir = context.filesDir
        val file = File(filesDir, "image.png")
        Log.d(TAG, file.name)
        withContext(Dispatchers.IO) {

            val inputStream = context.contentResolver.openInputStream(merchantData.displayPicture)
            val outputStream = FileOutputStream(file)
            inputStream!!.copyTo(outputStream)
            inputStream.close()

        }

        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("display_picture", file.name, requestBody)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response =
                    authAPI.postMerchantData(
                        image = imagePart,
                        name = merchantData.name,
                        phoneNumber = merchantData.phoneNumber
                    )
                Log.d(TAG, "success! ${response.string()}")
            } catch (e: Exception) {
                Log.d(TAG, "fiasco ${e.message}")
            }
        }
    }


}