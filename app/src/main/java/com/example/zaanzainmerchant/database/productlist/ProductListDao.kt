package com.example.zaanzainmerchant.database.productlist

import androidx.room.*
import com.example.zaanzainmerchant.utils.ProductDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( productList: List<RoomProductList>)

    @Query("SELECT * FROM room_product_list")
    fun getAll(): Flow<List<RoomProductList>>

    @Query("delete from room_product_list where room_product_list.id not in (:productIdLists)")
    fun deleteOldProducts( productIdLists: List<Int> )

    @Query("select * from room_product_list where room_product_list.id = (:productId)")
    fun selectProductById( productId: Int) : RoomProductList
}