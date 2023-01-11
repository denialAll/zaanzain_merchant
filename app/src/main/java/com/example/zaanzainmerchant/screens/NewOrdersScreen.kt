package com.example.zaanzainmerchant.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextButton
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.zaanzainmerchant.utils.Cart
import com.example.zaanzainmerchant.utils.CartItem
import com.example.zaanzainmerchant.utils.CartItems
import com.example.zaanzainmerchant.utils.Constants.TAG
import com.example.zaanzainmerchant.utils.IsAcceptedCartField
import com.example.zaanzainmerchant.viewmodels.NewOrdersViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewOrdersScreen(
    newOrdersViewModel: NewOrdersViewModel
){
    val newOrdersCartList by newOrdersViewModel.newOrdersCartList.collectAsState()
    val newOrdersCartItems by newOrdersViewModel.newOrdersCartItems.collectAsState()
    val acceptedOrdersCartList by newOrdersViewModel.acceptedOrdersCartList.collectAsState()
    val acceptedOrdersCartItems by newOrdersViewModel.acceptedOrdersCartItems.collectAsState()

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        newOrdersViewModel.refreshCartItems()
        refreshing = false
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)
    
    Box(Modifier.pullRefresh(state)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(state = rememberScrollState())
                .padding(horizontal = 6.dp)
                .pullRefresh(state)

        ) {
            Text("New Orders")
            newOrdersCartList.forEach { cart ->
                NewOrderItem(
                    cart = cart,
                    cartItems = newOrdersCartItems.filter { it.cart == cart.id },
                    newOrdersViewModel = newOrdersViewModel
                )
                Divider()
            }

            Text("Accepted Orders")
            acceptedOrdersCartList.forEach { cart ->
                AcceptedOrderItem(
                    cart = cart,
                    cartItems = acceptedOrdersCartItems.filter { it.cart == cart.id }
                )
                Divider()
            }
        }
        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewOrderItem(
    cart: Cart,
    cartItems: List<CartItems>,
    newOrdersViewModel: NewOrdersViewModel
){
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Order #${cart.id}")
        Text(text = "${cart.firstName} ${cart.lastName}")

        Text(text = formatDateTime(cart.created))

        for ((index, cartItem) in cartItems.withIndex()) {
            Row{
                Text("$index. ${cartItem.title}")
                Spacer(Modifier.weight(1f))
                Text("x ${cartItem.quantity}")
            }
        }

        Row {
            Spacer(
                Modifier
                    .weight(1f)
                    .fillMaxHeight())
            Text("Total Price: Rs ${totalPrice(cartItems)}")
        }

        Button(
            onClick = {
                newOrdersViewModel.updateIsCartAccepted(cart.id, IsAcceptedCartField(true))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Accept")
        }

        val openDialog = remember { mutableStateOf(false) }

        OutlinedButton(
            onClick = {
                openDialog.value = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reject")
        }

        if (openDialog.value){
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = "Reject Order?")
                },
                text = {
                    Text(
                        "Are you sure you want to reject the order?"
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                            newOrdersViewModel.updateIsCartAccepted(cart.id, IsAcceptedCartField(false))
                        }
                    ) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AcceptedOrderItem(
    cart: Cart,
    cartItems: List<CartItems>
){
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Order #${cart.id}")
        Text(text = "${cart.firstName} ${cart.lastName}")
        Text(text = formatDateTime(cart.created))

        for ((index, cartItem) in cartItems.withIndex()) {
            Row{
                Text("$index. ${cartItem.title}")
                Spacer(Modifier.weight(1f))
                Text("x ${cartItem.quantity}")
            }
        }
//        Spacer(modifier = Modifier.weight(1f).fillMaxHeight())
        Row {
            Spacer(
                Modifier
                    .weight(1f)
                    .fillMaxHeight())
            Text("Total Price: Rs ${totalPrice(cartItems)}")
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun formatDateTime(dateString: String): String {
    val dateTimePattern = "MM/dd' 'hh:mm:ss a"
    val formatter = DateTimeFormatter.ofPattern(dateTimePattern)
    val date = ZonedDateTime.parse(dateString)
    return date.format(formatter)
}

fun totalPrice(cartItems: List<CartItems>): Double {
    return cartItems.map { it.price.times(it.quantity) }.sum()
}

