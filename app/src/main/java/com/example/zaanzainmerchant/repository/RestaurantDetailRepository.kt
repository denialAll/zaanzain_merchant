package com.example.zaanzainmerchant.repository

import android.content.Context
import android.util.Log
import com.example.zaanzainmerchant.api.AuthAPI
import com.example.zaanzainmerchant.utils.Constants.TAG
import com.example.zaanzainmerchant.utils.MerchantData
import com.example.zaanzainmerchant.utils.MerchantInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class RestaurantDetailRepository @Inject constructor(private val authAPI: AuthAPI) {

    private val _merchantInfo = MutableStateFlow<MerchantInfo?>(null)
    val merchantInfo: StateFlow<MerchantInfo?>
        get() = _merchantInfo

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun uploadMerchantData(merchantData: MerchantData, context: Context) {

        val filesDir = context.filesDir
        val file = File(filesDir, "image.png")
        Log.d(TAG, file.name)
        if (merchantData.displayPicture != null){
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
                    if (merchantInfo.value == null){
                        val response =
                            authAPI.postMerchantData(
                                image = imagePart,
                                name = merchantData.name,
                                phoneNumber = merchantData.phoneNumber,
                                deliveryCharges = merchantData.deliveryCharges,
                                deliveryCutoff = merchantData.deliveryCutoff,
                                minOrder = merchantData.minOrder
                            )
                        Log.d(TAG, "success! ${response.string()}")
                    } else {
                        authAPI.updateMerchantData(
                            id = merchantInfo.value!!.id,
                            image = imagePart,
                            name = merchantData.name,
                            phoneNumber = merchantData.phoneNumber,
                            deliveryCharges = merchantData.deliveryCharges,
                            deliveryCutoff = merchantData.deliveryCutoff,
                            minOrder = merchantData.minOrder
                        )
                    }

                } catch (e: Exception) {
                    Log.d(TAG, "fiasco ${e.message}")
                }
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    if (merchantInfo.value != null){
                        authAPI.updateMerchantDataNoPicture(
                            id = merchantInfo.value!!.id,
                            name = merchantData.name,
                            phoneNumber = merchantData.phoneNumber,
                            deliveryCharges = merchantData.deliveryCharges,
                            deliveryCutoff = merchantData.deliveryCutoff,
                            minOrder = merchantData.minOrder
                        )
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "error message is ${e.message}")
                }
            }
        }





    }

    suspend fun getMerchantData(){
        val response = authAPI.getMerchantInfo()
        if (response.body().isNullOrEmpty()) {
            _merchantInfo.value = null
        } else {
            _merchantInfo.value = authAPI.getMerchantInfo().body()?.first()
        }
    }


}