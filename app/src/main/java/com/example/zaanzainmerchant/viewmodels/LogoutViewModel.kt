package com.example.zaanzainmerchant.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zaanzainmerchant.api.AuthAPI
import com.example.zaanzainmerchant.repository.UserRepository
import com.example.zaanzainmerchant.utils.Constants.TAG
import com.example.zaanzainmerchant.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    fun logout(){
        viewModelScope.launch {
            try {
                val response = userRepository.logoutUser()
                Log.d(TAG, "response from logout is: $response")
            } catch (e: Exception){
                Log.d(TAG, "Logout unsuccessful! ${e.message}")
            }
        }
    }
}