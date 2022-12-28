package com.example.zaanzainmerchant.repository

import com.example.zaanzainmerchant.api.AuthAPI
import com.example.zaanzainmerchant.database.AppDatabase
import com.example.zaanzainmerchant.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductListRepository @Inject constructor(private val database: AppDatabase, private val authAPI: AuthAPI) {

    val productList: Flow<List<ProductDetails>> = database.productListDao().getAll().map {
        DatabaseContainer(it).asDomainModel()
    }

    suspend fun refreshProductList() {
        withContext(Dispatchers.IO) {
            val productList = authAPI.getProductList()
            database.productListDao().insertAll(NetworkContainer(productList).asDatabaseModel())
            database.productListDao().deleteOldProducts( productList.map{ it.id })
        }
    }
}