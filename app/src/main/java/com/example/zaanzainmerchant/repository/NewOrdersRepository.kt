package com.example.zaanzainmerchant.repository

import android.util.Log
import com.example.zaanzainmerchant.api.AuthAPI
import com.example.zaanzainmerchant.utils.Cart
import com.example.zaanzainmerchant.utils.CartItem
import com.example.zaanzainmerchant.utils.CartItems
import com.example.zaanzainmerchant.utils.Constants.TAG
import com.example.zaanzainmerchant.utils.IsAcceptedCartField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class NewOrdersRepository @Inject constructor(private val authAPI: AuthAPI) {

//    private val _newOrders = MutableStateFlow<List<CartItem>>(emptyList())
//    val newOrders: StateFlow<List<CartItem>>
//        get() = _newOrders
//
//    private val _acceptedOrders = MutableStateFlow<List<CartItem>>(emptyList())
//    val acceptedOrders: StateFlow<List<CartItem>>
//        get() = _acceptedOrders
//
//    private val _newOrdersCarts = MutableStateFlow<List<Cart>>(emptyList())
//    val newOrdersCarts: StateFlow<List<Cart>>
//        get() = _newOrdersCarts
//
//    private val _acceptedOrdersCarts = MutableStateFlow<List<Cart>>(emptyList())
//    val acceptedOrdersCarts: StateFlow<List<Cart>>
//        get() = _acceptedOrdersCarts

//    suspend fun getNewOrders() {
//        _newOrders.value = authAPI.getNewItemList().sortedBy { it.cart }
//        Log.d(TAG, "new orders list size: ${newOrders.value.size}")
//    }
//
//    suspend fun getAcceptedOrders() {
//        _acceptedOrders.value = authAPI.getAcceptedItemList().sortedBy { it.cart }
//    }
//
//    suspend fun getNewOrdersCarts() {
//        _newOrdersCarts.value = authAPI.getNewOrderCartList().sortedBy { it.id }
//        Log.d(TAG, "new cart list size: ${newOrdersCarts.value.size}")
//
//    }
//
//    suspend fun getAcceptedOrdersCarts() {
//        _acceptedOrdersCarts.value = authAPI.getAcceptedOrderCartList().sortedBy { it.id }
//    }


    private val _cartItems = MutableStateFlow<List<CartItems>>(emptyList())
    val cartItems: StateFlow<List<CartItems>>
        get() = _cartItems

    suspend fun getNewAcceptedOrders() {
        _cartItems.value = authAPI.getNewAcceptedCartList()
    }

    suspend fun updateIsOrderAccepted(
        cartId: Int,
        isAccepted: IsAcceptedCartField
    ) {
        authAPI.updateOrderAcceptedStatus(cartId, isAccepted)
    }

}