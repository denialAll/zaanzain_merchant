package com.example.zaanzainmerchant

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.zaanzainmerchant.screens.MerchantApp
import com.example.zaanzainmerchant.ui.theme.ZaanZainMerchantTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZaanZainMerchantTheme {
                MerchantApp()
            }
        }
    }
}

