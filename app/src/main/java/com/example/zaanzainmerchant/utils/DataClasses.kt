package com.example.zaanzainmerchant.utils

import android.location.Address
import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
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
    val description: String,
    val price: Double,
    val category: String,
    val servings: Int = 1,
    @Json(name = "is_available") val isAvailable: Boolean = false ,
    @Json(name = "product_picture") val productPicture: String?,
    @Json(name = "category_order") val categoryOrder: Int
)

@JsonClass(generateAdapter = true)
data class UserRegistration(
    val username: String,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    val password: String
)
// cart model for use in previous orders screen
data class CartPrevious(
    val id: Int,
    val customer: Int? = null,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    val merchant: Int? = null,
    @Json(name = "phone_number") val phoneNumber: String,
    val created: String,
    val note: String,
    @Json(name = "address_name") val addressName: String?,
    @Json(name = "is_accepted") val isAccepted: Boolean?,
    @Json(name= "is_sent") val isSent: Boolean?
)

data class PreviousOrders(
    val count: Int,
    val next: Int?,
    val previous: Int?,
    val results: List<CartItems>
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


data class Category(
    val category: String,
    val categoryOrder: Int
)

data class MerchantData(
    val name: String,
    @Json(name = "phone_number") val phoneNumber: String,
    @Json(name = "is_open") val isOpen: Boolean = false,
    @Json(name = "delivery_charges") val deliveryCharges: Double,
    @Json(name = "delivery_free_cutoff") val deliveryCutoff: Double,
    @Json(name = "min_order") val minOrder: Double,
    @Json(name = "display_picture") val displayPicture: Uri?
)

data class MerchantOpenClosed(
    val id: Int,
    @Json(name = "is_open") val isOpen: Boolean,
    @Json(name = "is_verified") val isVerified: Boolean
)

data class MerchantInfo(
    val id: Int,
    val user: Int,
    val name: String,
    @Json(name = "phone_number") val phoneNumber: String,
    @Json(name = "is_open") val isOpen: Boolean,
    @Json(name = "is_verified") val isVerified: Boolean,
    @Json(name = "display_picture") val displayPicture: String,
    @Json(name = "delivery_charges") val deliveryCharges: Double?,
    @Json(name = "delivery_free_cutoff") val deliveryCutoff: Double?,
    @Json(name = "min_order") val minOrder: Double?,
    val username: String? = null,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String
)

data class ProductData(
    val title: String,
    val description: String = "",
    val price: Double,
    val category: String,
    val servings: Int = 1,
    @Json(name = "is_available") val isAvailable: Boolean,
    @Json(name = "category_order") val categoryOrder: Int,
    @Json(name = "product_picture") val productPicture: Uri?
)

data class ProductUpdateData(
    val title: String,
    val description: String = "",
    val price: Double,
    val category: String,
    val servings: Int = 1,
    @Json(name = "is_available") val isAvailable: Boolean,
    @Json(name = "category_order") val categoryOrder: Int
)

data class CartItem(
    val id: Int,
    val quantity: Int,
    val product: Int,
    val cart: Int,
    val title: String,
    val price: Double

    )

data class Cart(
    val id: Int,
    val customer: Int? = null,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    val merchant: Int? = null,
    @Json(name = "phone_number") val phoneNumber: String,
    val created: String,
    val note: String,
    val address: Int? = null,
    @Json(name = "address_name") val addressName: String? = null,
    val latitude: Double?,
    val longitude: Double?
)

data class IsAcceptedCartField(
    @Json(name = "is_accepted") val isAccepted: Boolean = true
)

data class IsSentCartField(
    @Json(name = "is_sent") val isSent: Boolean = true
)

data class CartItems(
    val id: Int,
    val quantity: Int,
    val product: Int,
    val cart: Int,
    val title: String,
    val price: Double,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    @Json(name = "phone_number") val phoneNumber: String,
    @Json(name = "is_accepted") val isAccepted: Boolean?,
    @Json(name = "is_sent") val isSent: Boolean?,
    val created: String,
    val note: String,
    val address: Int? = null,
    @Json(name = "address_name") val addressName: String? = null,
    val latitude: Double?,
    val longitude: Double?,
)

data class LocationUiState(
    val hasLocationAccess: Boolean,
    val place: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val address: Address? = null
)

data class PostAddress(
    val city: String,
    val address: String,
    val longitude: Double?,
    val latitude: Double?,
    val isCustomer: Boolean = true
)

data class GetAddress(
    val id: Int,
    val city: String,
    val address: String,
    val longitude: Double?,
    val latitude: Double?,
    val isCustomer: Boolean = true
)
