package com.example.zaanzainmerchant.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zaanzainmerchant.repository.NewOrdersRepository
import com.example.zaanzainmerchant.utils.Cart
import com.example.zaanzainmerchant.utils.CartItem
import com.example.zaanzainmerchant.utils.CartItems
import com.example.zaanzainmerchant.utils.Constants.TAG
import com.example.zaanzainmerchant.utils.IsAcceptedCartField
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
                _acceptedOrdersCartItems.value = it.filter { it.isAccepted == true }

                _newOrdersCartList.value = it
                    .filter { it.isAccepted == null }
                    .map {
                        Cart(
                            id = it.cart,
                            firstName = it.firstName,
                            lastName = it.lastName,
                            phoneNumber = it.phoneNumber,
                            created = it.created,
                            note = it.note
                        )
                    }.distinct()

                _acceptedOrdersCartList.value = it
                    .filter { it.isAccepted == true }
                    .map {
                        Cart(
                            id = it.cart,
                            firstName = it.firstName,
                            lastName = it.lastName,
                            phoneNumber = it.phoneNumber,
                            created = it.created,
                            note = it.note
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

//    val newOrdersCartItems: StateFlow<List<CartItem>>
//        get() = newOrdersRepository.newOrders
//
//    val acceptedOrdersCartItems: StateFlow<List<CartItem>>
//        get() = newOrdersRepository.acceptedOrders
//
//    val newOrdersCartList: StateFlow<List<Cart>>
//        get() = newOrdersRepository.newOrdersCarts
//
//    val acceptedOrdersCartList: StateFlow<List<Cart>>
//        get() = newOrdersRepository.acceptedOrdersCarts
//
//    init {
//        viewModelScope.launch {
//            refreshCart()
//        }
//    }

//

//
//    fun refreshCart(){
//        viewModelScope.launch {
//            try {
//                newOrdersRepository.getNewOrders()
//            } catch (e: Exception){
//                Log.d(TAG, "get new orders: ${e.message}")
//            }
//
//            try {
//                newOrdersRepository.getNewOrdersCarts()
//
//            } catch (e: Exception){
//                Log.d(TAG, "get new orders carts: ${e.message}")
//            }
//
//            try {
//                newOrdersRepository.getAcceptedOrders()
//
//            } catch (e: Exception){
//                Log.d(TAG, "get accepted orders ${e.message}")
//            }
//
//            try {
//                newOrdersRepository.getAcceptedOrdersCarts()
//            } catch (e: Exception){
//                Log.d(TAG, "get accepted orders carts ${e.message}")
//            }
//        }
//    }
}