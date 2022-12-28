package com.example.zaanzainmerchant.repository

import android.util.Log
import com.example.zaanzainmerchant.api.UserAPI
import com.example.zaanzainmerchant.network.NetworkResult
import com.example.zaanzainmerchant.utils.Constants.TAG
import com.example.zaanzainmerchant.utils.TokenManager
import com.example.zaanzainmerchant.utils.UserLogin
import com.example.zaanzainmerchant.utils.UserRegistration
import com.example.zaanzainmerchant.utils.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject


class UserRepository @Inject constructor(private val userAPI: UserAPI){

    @Inject
    lateinit var tokenManager: TokenManager

    private val _userResponse = MutableStateFlow<NetworkResult<UserResponse>?>(null)
    val userResponse: StateFlow<NetworkResult<UserResponse>?>
        get()  = _userResponse

    suspend fun registerUser(userRegistration: UserRegistration) {
        _userResponse.value = NetworkResult.Loading()
        val response = userAPI.registerUser(userRegistration)
        handleResponse(response)
    }

    suspend fun loginUser(userLogin: UserLogin) {
        _userResponse.value = NetworkResult.Loading()
        val response = userAPI.loginUser(userLogin)
        handleResponse(response)
    }

    private fun handleResponse(response: Response<UserResponse>){
        if (response.isSuccessful && response.body() != null) {
            tokenManager.saveToken(response.body()!!.token)
            _userResponse.value = NetworkResult.Success(response.body()!!)
            Log.d(TAG, "Successful network result is ${_userResponse.value}")
        }
        else if (response.errorBody() != null) {
            Log.d(TAG, "response.errorbody error occured!, ${response.message()}")
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponse.value = NetworkResult.Error("Error occured, try again with valid credentials!")
            Log.d(TAG, "Response with error body: Error obj is: ${errorObj}")
        }
        else {
            _userResponse.value = NetworkResult.Error("Something went wrong, please try again!")
            Log.d(TAG, "An error occured and no message body!")

        }
    }


}