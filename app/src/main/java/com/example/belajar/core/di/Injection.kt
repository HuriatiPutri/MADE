package com.example.belajar.core.di

import android.content.Context
import com.example.belajar.core.data.source.TourismRepository
import com.example.belajar.core.data.source.local.LocalDataSource
import com.example.belajar.core.data.source.local.room.TourismDatabase
import com.example.belajar.core.data.source.remote.RemoteDataSource
import com.example.belajar.core.data.source.remote.network.ApiConfig
import com.example.belajar.core.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context) :TourismRepository{
        val database = TourismDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(database.tourismDao())
        val appExecutors = AppExecutors()


        return TourismRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}