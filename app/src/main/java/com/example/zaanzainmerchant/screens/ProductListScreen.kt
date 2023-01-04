package com.example.zaanzainmerchant.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zaanzainmerchant.utils.Constants.TAG
import com.example.zaanzainmerchant.utils.ProductDetails
import com.example.zaanzainmerchant.viewmodels.ProductEditViewModel
import com.example.zaanzainmerchant.viewmodels.ProductListViewModel

@Composable
fun ProductListScreen(
    productListViewModel: ProductListViewModel,
    productEditViewModel: ProductEditViewModel,
    navController: NavController
) {
    val categoryList by productListViewModel.categoryList.collectAsState()
    val productList by productListViewModel.productList.collectAsState()

    LaunchedEffect(Unit) {
        productListViewModel.refreshProductList()
    }

    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        for (category in categoryList) {
            Log.d(TAG, "Category list is ${category}")
        }

        items(categoryList) { category ->
            Text(
                text = category.category,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge
            )
            for (productDetails in productList) {
                if (productDetails.category == category.category) {
                    ProductDetailCard(
                        product = productDetails,
                        productEditViewModel = productEditViewModel,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun ProductDetailCard(
    product: ProductDetails,
    productEditViewModel: ProductEditViewModel,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
        ){
            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = product.title,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.paddingFromBaseline(
                        top = 24.dp, bottom = 8.dp
                    )
                )
                Text(
                    text = product.description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ){
                    Text(
                        text = "Rs: ${product.price}",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            
            Spacer(modifier = Modifier.weight(1f).fillMaxHeight())

            TextButton(
                onClick = {
                    productEditViewModel.updateProductIdToEdit(product)
                    navController.navigate(Screen.ProductEdit.route)
                }
            ) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit product")
            }
        }
    }
}
