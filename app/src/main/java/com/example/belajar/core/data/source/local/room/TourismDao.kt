package com.example.belajar.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.belajar.core.data.source.local.entity.TourismEntity

@Dao
interface TourismDao {
    @Query("SELECT * FROM tourism")
    fun getAllTourism():LiveData<List<TourismEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTouris(tourism : List<TourismEntity>)

    @Update
    fun updateFavoriteTourism(tourism: TourismEntity)
}