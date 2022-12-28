package com.example.zaanzainmerchant.api

import com.example.zaanzainmerchant.utils.Basket
import com.example.zaanzainmerchant.utils.OrderReceived
import com.example.zaanzainmerchant.utils.ProductDetails
import com.example.zaanzainmerchant.utils.RestaurantDetails
import com.squareup.moshi.Json
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface AuthAPI {

    @Multipart
    @POST("merchant/listandcreate/")
    suspend fun postMerchantData(
        @Part image: MultipartBody.Part,
        @Part("name") name: String,
        @Part("phone_number") phoneNumber: String
    ): ResponseBody

    @Headers("Content-Type: application/json")
    @GET("products/list/")
    suspend fun getProductList(@Query("restaurantId") restaurantId: Int): List<ProductDetails>

    @Headers("Content-Type: application/json")
    @GET("products/detail/{id}/")
    suspend fun getProduct(@Path("id") id: Int): ProductDetails

    @Headers("Content-Type: application/json")
    @GET("merchant/listandcreate/")
    suspend fun getRestaurantList(): List<RestaurantDetails>

    @Headers("Content-Type: application/json")
    @POST("cart/custom/")
    suspend fun sendOrder(@Body cart: Basket): Response<OrderReceived>

}