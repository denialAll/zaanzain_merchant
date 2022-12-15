package com.example.zaanzainmerchant.api

import com.example.zaanzainmerchant.utils.UserLogin
import com.example.zaanzainmerchant.utils.UserRegistration
import com.example.zaanzainmerchant.utils.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserAPI {

    @Headers("Content-Type: application/json")
    @POST("api/login/")
    suspend fun loginUser(@Body userLoginData: UserLogin): Response<UserResponse>

    @Headers("Content-Type: application/json")
    @POST("api/register/")
    suspend fun registerUser(@Body userData: UserRegistration): Response<UserResponse>

}