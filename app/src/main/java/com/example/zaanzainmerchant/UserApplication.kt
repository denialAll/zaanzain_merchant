package com.example.zaanzainmerchant

import android.app.Application
import com.example.zaanzainmerchant.api.AuthAPI
import com.example.zaanzainmerchant.database.AppDatabase
import com.example.zaanzainmerchant.repository.RestaurantDetailRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class UserApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}