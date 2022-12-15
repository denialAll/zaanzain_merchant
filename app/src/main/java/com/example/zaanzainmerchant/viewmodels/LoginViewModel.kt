package com.example.zaanzainmerchant.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zaanzainmerchant.network.NetworkResult
import com.example.zaanzainmerchant.repository.UserRepository
import com.example.zaanzainmerchant.utils.Constants.TAG
import com.example.zaanzainmerchant.utils.TokenManager
import com.example.zaanzainmerchant.utils.UserLogin
import com.example.zaanzainmerchant.utils.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    @Inject
    lateinit var tokenManager: TokenManager

    val uiUserResponse: StateFlow<NetworkResult<UserResponse>?>
        get() = userRepository.userResponse

    var username by mutableStateOf("")
        private set
    fun updateUserName(userName: String){
        username = userName
    }
    var password by mutableStateOf("")
        private set
    fun updateUserPassword(userPassword: String){
        password = userPassword
    }


    fun resetInputFields(){
        username = ""
        password = ""
    }

    private val _tokenState = MutableStateFlow(false)
    val tokenState: StateFlow<Boolean>
        get()  = _tokenState

    private val _token = MutableStateFlow("")
    val token: StateFlow<String>
        get() = _token

    private val _makeToast = MutableStateFlow(false)
    val makeToast: StateFlow<Boolean>
        get()  = _makeToast

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String>
        get() = _errorMessage

    fun postLoginData() {
        viewModelScope.launch {
            try{
                userRepository.loginUser(getUserCredentials())

            } catch (e: Exception) {
                Log.d(TAG, "An error occured in catch block, ${e.message}")
            }
        }
    }

    private fun getUserCredentials(): UserLogin {
        return UserLogin(username, password)
    }
}