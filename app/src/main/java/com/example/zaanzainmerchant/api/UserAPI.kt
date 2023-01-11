package com.example.zaanzainmerchant.api

import com.example.zaanzainmerchant.utils.UserLogin
import com.example.zaanzainmerchant.utils.UserRegistration
import com.example.zaanzainmerchant.utils.UserResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {

    @Multipart
    @POST("test/listandcreate/")
    suspend fun postMerchantData(
        @Part image: MultipartBody.Part,
        @Part name: MultipartBody.Part
    ): ResponseBody

    @Headers("Content-Type: application/json")
    @POST("api/login/")
    suspend fun loginUser(
        @Body userLoginData: UserLogin
    ): Response<UserResponse>

    @Headers("Content-Type: application/json")
    @POST("api/register/")
    suspend fun registerUser(
        @Body userData: UserRegistration
    ): Response<UserResponse>

}