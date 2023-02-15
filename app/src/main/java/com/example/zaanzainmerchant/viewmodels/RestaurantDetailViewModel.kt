package com.example.zaanzainmerchant.viewmodels

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.zaanzainmerchant.repository.RestaurantDetailRepository
import com.example.zaanzainmerchant.utils.Constants.TAG
import com.example.zaanzainmerchant.utils.MerchantData
import com.example.zaanzainmerchant.utils.MerchantInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RestaurantDetailViewModel @Inject constructor(
    private val restaurantDetailRepository: RestaurantDetailRepository,
    application: Application
) : AndroidViewModel(application) {

    val merchantInfo: StateFlow<MerchantInfo?>
        get() = restaurantDetailRepository.merchantInfo

    var restaurantName by mutableStateOf("")
        private set
    fun updateRestaurantName(restaurantname: String){
        restaurantName = restaurantname
    }

    var phoneNumber by mutableStateOf("")
    private set
    fun updatePhoneNumber(phonenumber: String){
        phoneNumber = phonenumber
    }

    var deliveryCharges by mutableStateOf("")
        private set
    fun updateDeliveryCharges(deliveryCharge: String){
        deliveryCharges = deliveryCharge
    }

    var deliveryCutoff by mutableStateOf("")
        private set
    fun updateDeliveryCutoff(cutoff: String){
        deliveryCutoff = cutoff
    }

    var minOrder by mutableStateOf("")
        private set
    fun updateMinOrder(minimumOrder: String){
        minOrder = minimumOrder
    }

    var imageToUpload by mutableStateOf<Uri?>(null)
    private set
    fun updateImageToUpload(uri: Uri){
        imageToUpload = uri
    }

    var imageUri by mutableStateOf<Uri?>(null)
    private set
    fun updateImageUri(uri: Uri){
        imageUri = uri
    }


    fun uploadMerchantData(
        uri: Uri? = imageToUpload,
        context: Context = getApplication<Application>().applicationContext,
    ) {
        val data = MerchantData(
            name = restaurantName,
            phoneNumber = phoneNumber,
            displayPicture = uri,
            deliveryCharges = deliveryCharges.toDouble(),
            deliveryCutoff = deliveryCutoff.toDouble(),
            minOrder = minOrder.toDouble()
        )

        viewModelScope.launch {
            try {
                restaurantDetailRepository.uploadMerchantData(
                    merchantData = data,
                    context = context
                )
            } catch (e: Exception) {
                Log.d(TAG, "failed ${e.message}")
            }
        }
    }

    fun getMerchantData(){
        viewModelScope.launch {
            try {
                restaurantDetailRepository.getMerchantData()
                merchantInfo.value?.let {
                    updateRestaurantName(it.name)
                    updatePhoneNumber(it.phoneNumber)
                    updateImageUri(it.displayPicture.toUri())
                    updateDeliveryCharges(it.deliveryCharges.toString())
                    updateDeliveryCutoff(it.deliveryCutoff.toString())
                    updateMinOrder(it.minOrder.toString())
                }
            } catch (e: Exception){
                Log.d(TAG, "error occurred in getting merchant data: ${e.message}")
            }
        }
    }
}