package com.example.belajar.core.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.dicoding.tourismapp.core.utils.DataMapper
import com.example.belajar.core.data.source.local.LocalDataSource
import com.example.belajar.core.data.source.local.entity.TourismEntity
import com.example.belajar.core.data.source.remote.RemoteDataSource
import com.example.belajar.core.data.source.remote.network.ApiResponse
import com.example.belajar.core.data.source.remote.response.TourismResponse
import com.example.belajar.core.utils.AppExecutors

class TourismRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
){
    companion object{
        @Volatile
        private var instance : TourismRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): TourismRepository =
            instance ?: synchronized(this) {
                instance ?: TourismRepository(remoteData, localData, appExecutors)
            }

    }

    fun getAllTourism() : LiveData<Resource<List<TourismEntity>>> =
        object: NetworkBoundResource<List<TourismEntity>, List<TourismResponse>>(appExecutors){
            override fun loadFromDB(): LiveData<List<TourismEntity>> {
                return Transformations.map(localDataSource.getAllTourism()){
                    DataMapper.mapEntitesToDomain(it)
                }
            }
            override fun shouldFetch(data: List<TourismEntity>?): Boolean =
//                data == null || data.isEmpty()   load data from local db, true untuk selalu load dari internet
                true
            override fun createCall(): LiveData<ApiResponse<List<TourismResponse>>> =
                remoteDataSource.getAllTourism()

            override fun saveCallResult(data: List<TourismResponse>) {
                val tourismList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertTourism(tourismList)
            }
        }.asLiveData()

}