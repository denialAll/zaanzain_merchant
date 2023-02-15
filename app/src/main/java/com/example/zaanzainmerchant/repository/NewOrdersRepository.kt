package com.example.zaanzainmerchant.repository

import com.example.zaanzainmerchant.api.AuthAPI
import com.example.zaanzainmerchant.utils.CartItems
import com.example.zaanzainmerchant.utils.IsAcceptedCartField
import com.example.zaanzainmerchant.utils.IsSentCartField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class NewOrdersRepository @Inject constructor(private val authAPI: AuthAPI) {

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

    suspend fun updateIsOrderSent(
        cartId: Int,
        isSent: IsSentCartField
    ) {
        authAPI.updateOrderSentStatus(cartId, isSent)
    }

}