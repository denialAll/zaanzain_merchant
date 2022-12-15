package com.example.zaanzainmerchant.api

import com.example.zaanzainmerchant.utils.Basket
import com.example.zaanzainmerchant.utils.OrderReceived
import com.example.zaanzainmerchant.utils.ProductDetails
import com.example.zaanzainmerchant.utils.RestaurantDetails
import retrofit2.Response
import retrofit2.http.*

interface AuthAPI {

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