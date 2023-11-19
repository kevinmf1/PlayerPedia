package com.vinz.core.data.source.remote.network

import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.vinz.core.BuildConfig

object APIConfig {

    fun provideApiService(chucker: ChuckerInterceptor): APIService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(chucker)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(APIService::class.java)
    }
}