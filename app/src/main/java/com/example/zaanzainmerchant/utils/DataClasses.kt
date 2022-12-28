package com.example.zaanzainmerchant.utils

import android.net.Uri
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.coroutines.flow.MutableStateFlow

@JsonClass(generateAdapter = true)
data class RestaurantDetails(
    val id: Int,
    val user: Int,
    @Json(name = "display_picture") val displayPicture: String,
    val name: String,
    @Json(name = "is_open") val isOpen: Boolean,
    @Json(name = "phone_number") val phoneNumber: String
)

@JsonClass(generateAdapter = true)
data class ProductDetails(
    val id: Int,
    val title: String,
    val merchant: String,
    val description: String = "",
    val price: Double,
    val category: String,
    @Json(name = "category_order") val categoryOrder: Int,
    @Json(name = "product_picture") val productPicture: String?
)

@JsonClass(generateAdapter = true)
data class UserRegistration(
    val username: String,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    val password: String
)

@JsonClass(generateAdapter = true)
data class UserLogin(
    val username: String,
    val password: String
)

data class ResponseModel(
    val message: String
)

data class UserResponse(
    val token: String,
    val user: User
)

data class User(
    val id: Int,
    val username: String
)

@JsonClass(generateAdapter = true)
data class CartItem(
    val productId: Int,
    val quantity: MutableStateFlow<Int>,
    val title: String,
    val merchant: String,
    val description: String,
    val price: Double
)

@JsonClass(generateAdapter = true)
data class CartToSend(
    @Json(name="product") val productId: Int,
    val quantity: Int
)

@JsonClass(generateAdapter = true)
data class Cart(
    val address: Int,
    @Json(name = "phone_number") val phoneNumber: String,
    val note: String
)

data class Basket(
    val cart: Cart,
    val cartList: List<CartToSend>
)

data class OrderReceived(
    @Json(name = "order_status") val orderStatus: String
)

data class Category(
    val category: String,
    val categoryOrder: Int
)

data class MerchantData(
    val name: String,
    @Json(name = "phone_number") val phoneNumber: String,
    @Json(name = "is_open") val isOpen: Boolean = false,
    @Json(name = "display_picture") val displayPicture: Uri
)

data class ProductData(
    val title: String,
    val description: String = "",
    val price: Double,
    val category: String,
    @Json(name = "category_order") val categoryOrder: Int,
    val servings: Int = 1,
    @Json(name = "is_available") val isAvailable: Boolean = false,
    @Json(name = "product_picture") val productPicture: Uri? = null
)
