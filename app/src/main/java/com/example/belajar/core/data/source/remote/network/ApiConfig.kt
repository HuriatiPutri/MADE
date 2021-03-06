package com.example.belajar.core.data.source.remote.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun provideApiService() : ApiService{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dicoding-tourism-api.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return  retrofit.create(ApiService::class.java)
    }
}