package com.example.zaanzainmerchant.network

import com.example.zaanzainmerchant.api.AuthAPI
import com.example.zaanzainmerchant.api.UserAPI
import com.example.zaanzainmerchant.utils.Constants.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(moshi: Moshi): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

    @Singleton
    @Provides
    fun providesUserAPI(retrofitBuilder: Retrofit.Builder): UserAPI {
        return retrofitBuilder.build().create(UserAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesAuthAPI(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): AuthAPI {
        return retrofitBuilder.client(okHttpClient).build().create(AuthAPI::class.java)
    }

}