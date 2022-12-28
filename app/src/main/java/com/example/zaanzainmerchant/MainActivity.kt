package com.example.zaanzainmerchant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.zaanzainmerchant.screens.MerchantApp
import com.example.zaanzainmerchant.ui.theme.ZaanZainMerchantTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZaanZainMerchantTheme {
                MerchantApp()
            }
        }
    }
}

