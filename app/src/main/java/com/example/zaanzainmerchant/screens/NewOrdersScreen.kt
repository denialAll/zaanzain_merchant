package com.example.zaanzainmerchant.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.format.DateUtils.formatDateTime
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.zaanzainmerchant.R
import com.example.zaanzainmerchant.utils.*
import com.example.zaanzainmerchant.utils.Constants.TAG
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

    val context = LocalContext.current
    val newOrdersCartList by newOrdersViewModel.newOrdersCartList.collectAsState()
    val newOrdersCartItems by newOrdersViewModel.newOrdersCartItems.collectAsState()
    val acceptedOrdersCartList by newOrdersViewModel.acceptedOrdersCartList.collectAsState()
    val acceptedOrdersCartItems by newOrdersViewModel.acceptedOrdersCartItems.collectAsState()

    LaunchedEffect(Unit){
        newOrdersViewModel.refreshCartItems()
    }
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        newOrdersViewModel.refreshCartItems()
        refreshing = false
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)
    
    Box(
        Modifier
            .pullRefresh(state)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(state = rememberScrollState())
                .padding(horizontal = 6.dp)
                .pullRefresh(state),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            if (newOrdersCartList.isNotEmpty()){
                Text(
                    text = "New Orders: ${newOrdersCartList.size}",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                )
                newOrdersCartList.forEach { cart ->
                    CartTile(
                        cart = cart,
                        cartItems = newOrdersCartItems.filter { it.cart == cart.id },
                        newOrdersViewModel = newOrdersViewModel,
                        isNewCart = true
                    )
                }
            }
            else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text("No new Orders")
                }

            }

            if (acceptedOrdersCartList.isNotEmpty()){
                Text(
                    text = "Accepted Orders: ${acceptedOrdersCartList.size}",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp
                )
                acceptedOrdersCartList.forEach { cart ->
                    CartTile(
                        cart = cart,
                        cartItems = acceptedOrdersCartItems.filter { it.cart == cart.id },
                        newOrdersViewModel = newOrdersViewModel,
                        isNewCart = false
                    )
                }
            }
            else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text("No accepted Orders")
                }
            }
        }
        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CartTile(
    cart: Cart,
    cartItems: List<CartItems>,
    newOrdersViewModel: NewOrdersViewModel,
    isNewCart: Boolean
){
    Card(
        modifier = Modifier
            .padding(horizontal = 6.dp, vertical = 6.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant),
        elevation = 4.dp
    ){
        val context = LocalContext.current
        Column(
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 4.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "#${cart.id}",
                    fontWeight = FontWeight.Light
                )
                Spacer(Modifier.weight(1f))
                Text(text = formatDateTime(cart.created))

                val expanded =  remember { mutableStateOf(false) }
                androidx.compose.material.IconButton(
                    onClick = {
                        expanded.value = true
                    }
                ) {
                    androidx.compose.material.Icon(
                        Icons.Filled.MoreVert,
                        ""
                    )
                }
                NewOrdersDropDownMenu(
                    cart = cart,
                    cartItems = cartItems,
                    expanded = expanded
                )
            }

            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.Start
            ) {
                if (!isNewCart){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = cart.phoneNumber,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(end = 20.dp)
                        )
                        IconButton(
                            onClick = {
                                val uri = Uri.parse("tel:" + cart.phoneNumber)
                                val intent = Intent(Intent.ACTION_DIAL, uri)
                                try {
                                    context.startActivity(intent)
                                } catch(s: SecurityException){
                                    Toast.makeText(context, "An error occurred", Toast.LENGTH_LONG)
                                        .show()
                                }
                            }
                        ) {
                            Icon(imageVector = Icons.Filled.Call, contentDescription = null)
                        }
                    }
                }
                Text(
                    text = "${cart.firstName} ${cart.lastName}",
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = "${cart.addressName}",
                    fontWeight = FontWeight.Medium
                )
            }

            for ((index, cartItem) in cartItems.withIndex()) {
                Row {
                    Text(
                        text = "${index + 1}. ${cartItem.title}",
                        fontWeight = FontWeight.Light
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "x",
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = "${cartItem.quantity}",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Row {
                Spacer(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                )
                Text(
                    text = "Total Price: ",
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = "Rs ${totalPrice(cartItems)}",
                    fontWeight = FontWeight.Bold
                )
            }

            if (isNewCart) {
                Button(
                    onClick = {
                        newOrdersViewModel.updateIsCartAccepted(
                            cart.id,
                            IsAcceptedCartField(true)
                        )
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

                if (openDialog.value) {
                    RejectOrderDialog(cart = cart, newOrdersViewModel = newOrdersViewModel, openDialog)
                }
            } else {
                Button(
                    onClick = {
                        newOrdersViewModel.updateIsCartSent(
                            cartId = cart.id,
                            isSent = IsSentCartField(true)
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Order sent")
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun formatDateTime(dateString: String): String {
    val dateTimePattern = "MM/dd' 'hh:mm:ss a"
    val formatter = DateTimeFormatter.ofPattern(dateTimePattern)
    val zoneId = ZoneId.of("Asia/Karachi")
    val date = ZonedDateTime.parse(dateString).withZoneSameInstant(zoneId)
    return date.format(formatter)
}

fun totalPrice(cartItems: List<CartItems>): Double {
    return cartItems.map { it.price.times(it.quantity) }.sum()
}

@RequiresApi(Build.VERSION_CODES.O)
fun shareOrder(context: Context, cart: Cart, cartItems: List<CartItems>){
    val summary = summary(cart, cartItems)
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "ZaanZain Order Details")
        putExtra(Intent.EXTRA_TEXT, summary)
    }

    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.app_name)
        )
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun summary(cart: Cart, cartItems: List<CartItems>): String {
    var cartItemsString = ""
    for ((index, cartItem) in cartItems.withIndex()) {
        cartItemsString += "${index + 1}. ${cartItem.title} x ${cartItem.quantity} \n"
    }
    cartItemsString += "\n"
    val summary = "#${cart.id}      ${formatDateTime(cart.created)} \n \n" +
            "${cart.phoneNumber} \n \n" +
            "${cart.firstName} ${cart.lastName} \n \n" +
            "${cart.addressName} \n \n" +
            cartItemsString +
            "Total price: ${totalPrice(cartItems)}"

    return summary
}

@Composable
fun RejectOrderDialog(
    cart: Cart,
    newOrdersViewModel: NewOrdersViewModel,
    openDialog: MutableState<Boolean>
){
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
                    newOrdersViewModel.updateIsCartAccepted(
                        cart.id,
                        IsAcceptedCartField(false)
                    )
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewOrdersDropDownMenu(
    cart: Cart,
    cartItems: List<CartItems>,
    expanded: MutableState<Boolean>
){
    val gmmIntentUri = Uri.parse("geo:${cart.latitude},${cart.longitude}?z=21")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")

    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = {
            expanded.value = false
        },
        offset = DpOffset(x = (170).dp, y = (-10).dp)
    ) {
        val context = LocalContext.current
        androidx.compose.material.DropdownMenuItem(
            onClick = {
                startActivity(context, mapIntent, null)
                expanded.value = false
            }
        ) {
            Text(text = "Open in Maps")
        }
        androidx.compose.material.DropdownMenuItem(
            onClick = {
                shareOrder(context, cart, cartItems)
                expanded.value = false
            }
        ) {
            Text(text = "Share")
        }
    }
}
