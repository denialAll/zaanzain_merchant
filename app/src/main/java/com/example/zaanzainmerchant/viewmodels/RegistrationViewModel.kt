package com.example.zaanzainmerchant.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zaanzainmerchant.api.UserAPI
import com.example.zaanzainmerchant.network.NetworkResult
import com.example.zaanzainmerchant.repository.UserRepository
import com.example.zaanzainmerchant.utils.UserRegistration
import com.example.zaanzainmerchant.utils.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(val userRepository: UserRepository) : ViewModel() {

    val uiUserResponse: StateFlow<NetworkResult<UserResponse>?>
        get() = userRepository.userResponse

    var username by mutableStateOf("")
        private set
    fun updateUserName(userName: String){
        username = userName
    }

    var firstName by mutableStateOf("")
    fun updateFirstName(firstname: String) {
        firstName = firstname
    }

    var lastName by mutableStateOf("")
    fun updateLastName(lastname: String) {
        lastName = lastname
    }

    var password by mutableStateOf("")
        private set

    fun updateUserPassword(userPassword: String){
        password = userPassword
    }

    var confirmPassword by mutableStateOf("")
        private set

    fun updateUserConfirmPassword(userPassword: String){
        confirmPassword = userPassword
    }

    fun resetInputFields(){
        username = ""
        firstName = ""
        lastName = ""
        password = ""
    }

    fun postUserRegistrationData(){
        viewModelScope.launch {
            try {
                val response = userRepository.registerUser(
                    userRegistration = UserRegistration(
                        username,
                        firstName,
                        lastName,
                        password
                    )
                )
                Log.d("Dooggoo", "response is ${response}")

            } catch (e: Exception) {
                Log.d("Dooggoo", "son of ${e.message}")
            }
        }
    }

}