package com.example.zaanzainmerchant.api

import androidx.room.Update
import com.example.zaanzainmerchant.utils.*
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

    @Multipart
    @POST("products/create/")
    suspend fun postProductData(
        @Part image: MultipartBody.Part?,
        @Part("title") title: String,
        @Part("description") description : String,
        @Part("price") price: Double,
        @Part("category") category: String,
        @Part("category_order") categoryOrder: Int,
        @Part("is_available") isAvailable: Boolean,
        @Part("servings") servings: Int
    ): ResponseBody


    @Headers("Content-Type: application/json")
    @GET("products/list/merchant-products/")
    suspend fun getProductList(): List<ProductDetails>


    @Headers("Content-Type: application/json")
    @PATCH("products/rud/{id}/")
    suspend fun updateProductData(
        @Path("id") id: Int,
        @Body productData: ProductUpdateData
    ): ResponseBody

    @Multipart
    @PATCH("products/rud/{id}/")
    suspend fun updateProductImage(
        @Path("id") id: Int,
        @Part image: MultipartBody.Part
    ): ResponseBody

//    @Headers("Content-Type: application/json")
//    @GET("cart-item/newitemlist/")
//    suspend fun getNewItemList(): List<CartItem>
//
//    @Headers("Content-Type: application/json")
//    @GET("cart-item/accepteditemlist/")
//    suspend fun getAcceptedItemList(): List<CartItem>
//
//    @Headers("Content-Type: application/json")
//    @GET("cart/new-orders/")
//    suspend fun getNewOrderCartList(): List<Cart>
//
//    @Headers("Content-Type: application/json")
//    @GET("cart/accepted-orders/")
//    suspend fun getAcceptedOrderCartList(): List<Cart>

    @Headers("Content-Type: application/json")
    @GET("cart-item/newandacceptedlist/")
    suspend fun getNewAcceptedCartList(): List<CartItems>

    @Headers("Content-Type: application/json")
    @PATCH("cart/rud/{id}/")
    suspend fun updateOrderAcceptedStatus(
        @Path("id") id: Int,
        @Body isAccepted: IsAcceptedCartField
    ): ResponseBody

    @Headers("Content-Type: application/json")
    @POST("api/logout/")
    suspend fun logout(): ResponseBody

    @Headers("Content-Type: application/json")
    @GET("products/detail/{id}/")
    suspend fun getProduct(@Path("id") id: Int): ProductDetails

    @Headers("Content-Type: application/json")
    @GET("merchant/listandcreate/")
    suspend fun getRestaurantList(): List<RestaurantDetails>


}