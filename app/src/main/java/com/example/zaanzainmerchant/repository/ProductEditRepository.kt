package com.example.zaanzainmerchant.repository

import com.example.zaanzainmerchant.api.AuthAPI
import com.example.zaanzainmerchant.database.AppDatabase
import com.example.zaanzainmerchant.utils.*
import javax.inject.Inject

class ProductEditRepository @Inject constructor(
    private val authAPI: AuthAPI,
    private val database: AppDatabase
    ) {

}