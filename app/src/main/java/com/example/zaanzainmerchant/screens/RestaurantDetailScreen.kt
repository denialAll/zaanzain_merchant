package com.example.zaanzainmerchant.screens

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.zaanzainmerchant.utils.Constants.TAG
import com.example.zaanzainmerchant.viewmodels.RestaurantDetailViewModel

@Composable
fun RestaurantDetailScreen(
    restaurantDetailViewModel: RestaurantDetailViewModel
){
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = restaurantDetailViewModel.restaurantName,
            onValueChange = { restaurantDetailViewModel.updateRestaurantName(it) },
            label = { Text(text = "Restaurant Name") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        TextField(
            value = restaurantDetailViewModel.phoneNumber,
            onValueChange = { restaurantDetailViewModel.updatePhoneNumber(it) },
            label = { Text(text = "Phone Number") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            )
        )

        val pickMedia = rememberLauncherForActivityResult(
            ActivityResultContracts.PickVisualMedia()){ uri ->
            Log.d(TAG, uri.toString())
        }

        Button(
            onClick = {
                pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
            }
        ) {
            Text(text = "Choose photo")
        }
//
    }
}