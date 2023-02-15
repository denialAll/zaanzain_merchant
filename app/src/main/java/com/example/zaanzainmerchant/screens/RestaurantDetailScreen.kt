package com.example.zaanzainmerchant.screens

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.zaanzainmerchant.utils.Constants.TAG
import com.example.zaanzainmerchant.viewmodels.RestaurantDetailViewModel

@Composable
fun RestaurantDetailScreen(
    restaurantDetailViewModel: RestaurantDetailViewModel
){
    LaunchedEffect(Unit){
        restaurantDetailViewModel.getMerchantData()
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState(initial = 0))
    ) {
        TextField(
            value = restaurantDetailViewModel.restaurantName,
            onValueChange = { restaurantDetailViewModel.updateRestaurantName(it) },
            label = { Text(text = "Restaurant Name") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.padding(top = 12.dp),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary
            )
        )

        TextField(
            value = restaurantDetailViewModel.phoneNumber,
            onValueChange = { restaurantDetailViewModel.updatePhoneNumber(it) },
            label = { Text(text = "Phone Number") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary
            )
        )

        TextField(
            value = restaurantDetailViewModel.deliveryCharges,
            onValueChange = { restaurantDetailViewModel.updateDeliveryCharges(it) },
            label = { Text(text = "Delivery Charge") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary
            )
        )

        TextField(
            value = restaurantDetailViewModel.deliveryCutoff,
            onValueChange = { restaurantDetailViewModel.updateDeliveryCutoff(it) },
            label = { Text(text = "Delivery charge cut-off") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary
            )
        )

        TextField(
            value = restaurantDetailViewModel.minOrder,
            onValueChange = { restaurantDetailViewModel.updateMinOrder(it) },
            label = { Text(text = "Minimum Order") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary
            )
        )

        val pickMedia = rememberLauncherForActivityResult(
            PickVisualMedia()){ uri ->
                if (uri != null) {
                    restaurantDetailViewModel.updateImageToUpload(uri)
                    Log.d(TAG, "selected media: $uri")
                } else {
                    Log.d(TAG, "No media selected")
                }
        }


        if (restaurantDetailViewModel.imageUri == null) {
            Button(
                onClick = {
                    pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = "Choose photo")
            }
        } else {
            var imageModel = restaurantDetailViewModel.imageUri
            if (restaurantDetailViewModel.imageToUpload != null){
                imageModel = restaurantDetailViewModel.imageToUpload
            }
            AsyncImage(
                model = imageModel,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(230.dp)
                    .border(
                        BorderStroke(1.dp, Color.Black)
                    )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ){
                Button(
                    onClick = {
                        pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(text = "Change Photo")
                }
                Button(
                    onClick = { restaurantDetailViewModel.uploadMerchantData() },
                    enabled = restaurantDetailViewModel.imageUri != null,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ){
                    Text(text = "Upload")
                }
            }
        }
    }
}