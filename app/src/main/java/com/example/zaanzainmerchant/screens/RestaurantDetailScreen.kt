package com.example.zaanzainmerchant.screens

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.zaanzainmerchant.utils.Constants.TAG
import com.example.zaanzainmerchant.viewmodels.RestaurantDetailViewModel
import java.io.File

@Composable
fun RestaurantDetailScreen(
    restaurantDetailViewModel: RestaurantDetailViewModel
){
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
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
            modifier = Modifier.padding(top = 12.dp)
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
            PickVisualMedia()){ uri ->
                if (uri != null) {
                    Log.d(TAG, "selected media: $uri")
                } else {
                    Log.d(TAG, "No media selected")
                }
        }




        if (restaurantDetailViewModel.imageUri == null) {
            Button(
                onClick = {
                    pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                }
            ) {
                Text(text = "Choose photo")
            }
        } else {
            AsyncImage(
                model = restaurantDetailViewModel.imageUri,
                contentDescription = null
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Button(
                    onClick = {
                        pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                    }
                ) {
                    Text(text = "Choose  a different photo")
                }
                Button(
                    onClick = { restaurantDetailViewModel.uploadMerchantData() },
                    enabled = restaurantDetailViewModel.imageUri != null
                ){
                    Text(text = "Upload")
                }
            }
        }



//
    }
}