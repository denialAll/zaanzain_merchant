package com.example.zaanzainmerchant.api

import com.example.zaanzainmerchant.utils.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface AuthAPI {

    @Multipart
    @POST("merchant/listandcreate/")
    suspend fun postMerchantData(
        @Part image: MultipartBody.Part,
        @Part("name") name: String,
        @Part("phone_number") phoneNumber: String,
        @Part("delivery_charges") deliveryCharges: Double,
        @Part("delivery_free_cutoff") deliveryCutoff: Double,
        @Part("min_order") minOrder: Double
    ): ResponseBody

    @Multipart
    @PATCH("merchant/rud/{id}/")
    suspend fun updateMerchantData(
        @Path("id") id: Int,
        @Part image: MultipartBody.Part,
        @Part("name") name: String,
        @Part("phone_number") phoneNumber: String,
        @Part("delivery_charges") deliveryCharges: Double,
        @Part("delivery_free_cutoff") deliveryCutoff: Double,
        @Part("min_order") minOrder: Double
    ): ResponseBody

    @Multipart
    @PATCH("merchant/rud/{id}/")
    suspend fun updateMerchantDataNoPicture(
        @Path("id") id: Int,
        @Part("name") name: String,
        @Part("phone_number") phoneNumber: String,
        @Part("delivery_charges") deliveryCharges: Double,
        @Part("delivery_free_cutoff") deliveryCutoff: Double,
        @Part("min_order") minOrder: Double
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

    @Headers("Content-Type: application/json")
    @GET("cart-item/merchant/previous-orders/")
    suspend fun getPreviousOrders(
        @Query("page") pageNumber: Int
    ): PreviousOrders

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
    @PATCH("cart/rud/{id}/")
    suspend fun updateOrderSentStatus(
        @Path("id") id: Int,
        @Body isSent: IsSentCartField
    ): ResponseBody

    @Headers("Content-Type: application/json")
    @POST("api/logout/")
    suspend fun logout(): ResponseBody

    @Headers("Content-Type: application/json")
    @GET("merchant/listandcreate/")
    suspend fun getMerchantInfo(): Response<List<MerchantInfo>>

    @Multipart
    @PATCH("merchant/rud/{id}/")
    suspend fun updateMerchantOpenClose(
        @Path("id") id: Int,
        @Part("is_open") isOpen: Boolean
    ): ResponseBody

    @Headers("Content-Type: application/json")
    @POST("address/listandcreate/")
    suspend fun postAddress(@Body address: PostAddress): Response<GetAddress>

    @Headers("Content-Type: application/json")
    @GET("address/listandcreate/")
    suspend fun getAddresses(): Response<List<GetAddress>>

    @Headers("Content-Type: application/json")
    @DELETE("address/rud/{id}/")
    suspend fun deleteAddresses(@Path("id") id: Int): Response<GetAddress>

}