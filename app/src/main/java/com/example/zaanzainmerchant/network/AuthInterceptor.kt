package com.example.zaanzainmerchant.network

import android.util.Log
import com.example.zaanzainmerchant.utils.Constants.TAG
import com.example.zaanzainmerchant.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    @Inject
    lateinit var tokenManager: TokenManager

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        val token = tokenManager.getToken()
        Log.d(TAG, "Token from the interceptor is $token")
        request.addHeader("Authorization", "TOKEN $token")
        return chain.proceed(request.build())
    }
}