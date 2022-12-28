package com.example.zaanzainmerchant.database.productlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room_product_list")
data class RoomProductList(
    @PrimaryKey val id: Int,
    @ColumnInfo() val title: String,
    @ColumnInfo() val merchant: String,
    @ColumnInfo() val description: String="",
    @ColumnInfo() val price: Double,
    @ColumnInfo() val category: String,
    @ColumnInfo(name = "category_order") val categoryOrder: Int,
    @ColumnInfo(name = "product_picture") val productPicture: String?
)