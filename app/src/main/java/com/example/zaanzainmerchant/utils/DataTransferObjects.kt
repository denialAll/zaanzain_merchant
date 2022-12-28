package com.example.zaanzainmerchant.utils

import com.example.zaanzainmerchant.database.productlist.RoomProductList
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class NetworkContainer(val productList: List<ProductDetails>)

@JsonClass(generateAdapter = true)
data class DatabaseContainer(val productList: List<RoomProductList>)

fun NetworkContainer.asDatabaseModel(): List<RoomProductList> {
    return productList.map {
        RoomProductList(
            id = it.id,
            title = it.title,
            merchant = it.merchant,
            description = it.description,
            price = it.price,
            category = it.category,
            categoryOrder = it.categoryOrder,
            productPicture = it.productPicture
        )
    }
}

fun DatabaseContainer.asDomainModel(): List<ProductDetails> {
    return productList.map {
        ProductDetails(
            id = it.id,
            title = it.title,
            merchant = it.merchant,
            description = it.description,
            price = it.price,
            category = it.category,
            categoryOrder = it.categoryOrder,
            productPicture = it.productPicture
        )
    }
}