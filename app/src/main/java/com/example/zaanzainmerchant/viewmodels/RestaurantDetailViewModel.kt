package com.example.zaanzainmerchant.viewmodels

import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RestaurantDetailViewModel : ViewModel() {

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

    var displayPicture by mutableStateOf("")
    private set
    fun updateDisplayPicture(displaypicture: String){
        displayPicture = displaypicture
    }
}