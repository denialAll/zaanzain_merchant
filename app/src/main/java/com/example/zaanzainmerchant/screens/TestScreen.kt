package com.example.zaanzainmerchant.screens

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.zaanzainmerchant.utils.Constants
import com.example.zaanzainmerchant.viewmodels.RestaurantDetailViewModel

@Composable
fun TestScreen(viewModel: RestaurantDetailViewModel){

    val pickMedia = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ){ uri ->
        if (uri != null) {
            viewModel.updateImageUri(uri)
            Log.d(Constants.TAG, "selected media: $uri")
        } else {
            Log.d(Constants.TAG, "No media selected")

        }
    }

    Column {
        Button(
            onClick = {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        ) {
            Text(text = "Choose photo")
        }

        AsyncImage(model = viewModel.imageUri, contentDescription = null)

        Button(
            onClick = {
                if (viewModel.imageUri != null){
                    viewModel.uploadMerchantData()
                }
            },
            enabled =  viewModel.imageUri != null
        ){
            Text(text = "Upload")
        }
    }

}