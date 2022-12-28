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
import com.example.zaanzainmerchant.repository.RestaurantDetailRepository
import com.example.zaanzainmerchant.utils.Constants.TAG
import com.example.zaanzainmerchant.utils.MerchantData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RestaurantDetailViewModel @Inject constructor(
    private val restaurantDetailRepository: RestaurantDetailRepository,
    application: Application
) : AndroidViewModel(application) {

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

    var imageUri by mutableStateOf<Uri?>(null)
    private set
    fun updateImageUri(uri: Uri){
        imageUri = uri
    }


    fun uploadMerchantData(
        uri: Uri = imageUri!!,
        context: Context = getApplication<Application>().applicationContext,
    ) {
        val data = MerchantData(
            name = restaurantName,
            phoneNumber = phoneNumber,
            displayPicture = uri
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
}