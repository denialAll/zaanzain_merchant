package com.example.zaanzainmerchant.screens

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.zaanzainmerchant.R
import com.example.zaanzainmerchant.utils.Constants
import com.example.zaanzainmerchant.viewmodels.AddProductViewModel

@Composable
fun AddProductScreen(
    addProductViewModel: AddProductViewModel
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = ScrollState(0)),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = addProductViewModel.title,
            onValueChange = { addProductViewModel.updateTitle(it) },
            label = { Text(text = "Title") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.padding(top = 12.dp)
        )

        TextField(
            value = addProductViewModel.description,
            onValueChange = { addProductViewModel.updateDescription(it) },
            label = { Text(text = "Product Description") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        TextField(
            value = addProductViewModel.category,
            onValueChange = { addProductViewModel.updateCategory(it) },
            label = { Text(text = "Product Category") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        TextField(
            value = addProductViewModel.categoryOrder,
            onValueChange = { addProductViewModel.updateCategoryOrder(it) },
            label = { Text(text = "Product Category order") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        TextField(
            value = addProductViewModel.price,
            onValueChange = { addProductViewModel.updatePrice(it) },
            label = { Text(text = "Product Price") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        TextField(
            value = addProductViewModel.servings,
            onValueChange = { addProductViewModel.updateServings(it) },
            label = { Text(text = "Product serves?") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Is the product available")
            Switch(
                checked = addProductViewModel.isAvailable,
                onCheckedChange = { addProductViewModel.updateIsProductAvailable(it) }
            )
        }
        val pickMedia = rememberLauncherForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ){ uri ->
            if (uri != null) {
                addProductViewModel.updateImageUri(uri)
                Log.d(Constants.TAG, "selected media: $uri")
            } else {
                Log.d(Constants.TAG, "No media selected")
            }
        }

        TextButton(
            onClick = {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }) {
                Icon(painterResource(R.drawable.photo_library), "")
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Add product photo")
            }

        Button(
            onClick = { addProductViewModel.uploadProductData() }
        ){
            Text("Upload product")
        }
    }
}