package com.example.zaanzainmerchant.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zaanzainmerchant.repository.NewOrdersRepository
import com.example.zaanzainmerchant.utils.*
import com.example.zaanzainmerchant.utils.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Locale.filter
import javax.inject.Inject


@HiltViewModel
class NewOrdersViewModel @Inject constructor(private val newOrdersRepository: NewOrdersRepository): ViewModel(){

    private val _newOrdersCartItems = MutableStateFlow<List<CartItems>>(emptyList())
    val newOrdersCartItems: StateFlow<List<CartItems>>
        get() = _newOrdersCartItems

    private val _acceptedOrdersCartItems = MutableStateFlow<List<CartItems>>(emptyList())
    val acceptedOrdersCartItems: StateFlow<List<CartItems>>
        get() = _acceptedOrdersCartItems

    private val _newOrdersCartList = MutableStateFlow<List<Cart>>(emptyList())
    val newOrdersCartList: StateFlow<List<Cart>>
        get() = _newOrdersCartList

    private val _acceptedOrdersCartList = MutableStateFlow<List<Cart>>(emptyList())
    val acceptedOrdersCartList: StateFlow<List<Cart>>
        get() = _acceptedOrdersCartList


    init {
        viewModelScope.launch {
            refreshCartItems()
            newOrdersRepository.cartItems.collect {
                _newOrdersCartItems.value = it.filter { it.isAccepted == null }
                _acceptedOrdersCartItems.value = it.filter { it.isAccepted == true && it.isSent == false }

                _newOrdersCartList.value = it
                    .filter { it.isAccepted == null }
                    .map {
                        Cart(
                            id = it.cart,
                            firstName = it.firstName,
                            lastName = it.lastName,
                            phoneNumber = it.phoneNumber,
                            created = it.created,
                            note = it.note,
                            address = it.address,
                            addressName = it.addressName,
                            latitude = it.latitude,
                            longitude = it.longitude
                        )
                    }.distinct()

                _acceptedOrdersCartList.value = it
                    .filter { it.isAccepted == true && it.isSent == false }
                    .map {
                        Cart(
                            id = it.cart,
                            firstName = it.firstName,
                            lastName = it.lastName,
                            phoneNumber = it.phoneNumber,
                            created = it.created,
                            note = it.note,
                            address = it.address,
                            addressName = it.addressName,
                            latitude = it.latitude,
                            longitude = it.longitude
                        )
                    }.distinct()
            }
        }
    }

    fun refreshCartItems(){
        viewModelScope.launch {
            try {
                newOrdersRepository.getNewAcceptedOrders()
            } catch (e: Exception){
                Log.d(TAG, "refreshing carts failed: ${e.message}")
            }
        }
    }

    fun updateIsCartAccepted(
        cartId: Int,
        isAccepted: IsAcceptedCartField
    ){
        viewModelScope.launch {
            try {
                newOrdersRepository.updateIsOrderAccepted(cartId, isAccepted)
            } catch (e: Exception){
                Log.d(TAG, "${e.message}")
            }
            refreshCartItems()
        }
    }

    fun updateIsCartSent(
        cartId: Int,
        isSent: IsSentCartField
    ){
        viewModelScope.launch {
            try {
                newOrdersRepository.updateIsOrderSent(cartId, isSent)
            } catch (e: Exception){
                Log.d(TAG, "${e.message}")
            }
            refreshCartItems()
        }
    }
}