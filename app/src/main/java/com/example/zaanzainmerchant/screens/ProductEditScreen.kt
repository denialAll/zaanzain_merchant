package com.example.zaanzainmerchant.screens

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.zaanzainmerchant.R
import com.example.zaanzainmerchant.utils.Constants
import com.example.zaanzainmerchant.viewmodels.ProductEditViewModel
import com.example.zaanzainmerchant.viewmodels.ProductListViewModel


@Composable
fun ProductEditScreen(
    productEditViewModel: ProductEditViewModel,
    productListViewModel: ProductListViewModel,
    navController: NavController
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = ScrollState(0)),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val title by productEditViewModel.title.collectAsState()

        TextField(
            value = title ?: "",
            onValueChange = { productEditViewModel.updateTitle(it) },
            label = { Text(text = "Title") },
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

        val description by productEditViewModel.description.collectAsState()

        TextField(
            value = description ?: "",
            onValueChange = { productEditViewModel.updateDescription(it) },
            label = { Text(text = "Product Description") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary
            )
        )

        val category by productEditViewModel.category.collectAsState()

        TextField(
            value = category ?: "",
            onValueChange = { productEditViewModel.updateCategory(it) },
            label = { Text(text = "Product Category") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary
            )
        )

        val categoryOrder by productEditViewModel.categoryOrder.collectAsState()

        TextField(
            value = categoryOrder ?: "1",
            onValueChange = { productEditViewModel.updateCategoryOrder(it) },
            label = { Text(text = "Product Category order") },
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

        val price by productEditViewModel.price.collectAsState()

        TextField(
            value = price ?: "0.0",
            onValueChange = { productEditViewModel.updatePrice(it) },
            label = { Text(text = "Product Price") },
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

        val servings by productEditViewModel.servings.collectAsState()

        TextField(
            value = servings ?: "1",
            onValueChange = { productEditViewModel.updateServings(it) },
            label = { Text(text = "Product serves?") },
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

        val isAvailable by productEditViewModel.isProductAvailable.collectAsState()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Is the product available")
            Switch(
                checked = isAvailable ?: false,
                onCheckedChange = { productEditViewModel.updateIsProductAvailable(it) }
            )
        }

        TextButton(
            onClick = {
                navController.navigate(Screen.UpdateProductImage.route) {
                    popUpTo(Screen.ProductEdit.route)
                }
            }) {
            Icon(
                painterResource(R.drawable.photo_library),
                "",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Add product photo", color = MaterialTheme.colorScheme.primary)
        }

        Button(
            onClick = {
                productEditViewModel.updateProductData()
                navController.navigate(Screen.ProductList.route){
                    popUpTo(Screen.ProductList.route) { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ){
            Text("Update product")
        }
    }
}


@Composable
fun ImageUpdateScreen(
    productEditViewModel: ProductEditViewModel,
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val productPrevImage by productEditViewModel.productImage.collectAsState()
        val imageUri by productEditViewModel.imageUri.collectAsState()

        val pickMedia = rememberLauncherForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ){ uri ->
            if (uri != null) {
                productEditViewModel.updateImageUri(uri)
                Log.d(Constants.TAG, "selected media: $uri")
            } else {
                Log.d(Constants.TAG, "No media selected")
            }
        }

        if (imageUri == null) {
            AsyncImage(model = productPrevImage, contentDescription = "")

            TextButton(
                onClick = {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }) {
                Icon(painterResource(R.drawable.photo_library), "")
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Change product picture")
            }
        } else {
            AsyncImage(
                model = imageUri,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(230.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Button(onClick = {
                    productEditViewModel.updateImageUri(null)
                }) {
                    Text("Cancel")
                }
                Button(onClick = {
                    productEditViewModel.updateProductImage()
                }) {
                    Text("Upload")
                }
            }
        }


    }
}