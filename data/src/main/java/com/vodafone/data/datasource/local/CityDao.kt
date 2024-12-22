package com.vodafone.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vodafone.data.model.City

@Dao
interface CityDao {


    @Query("SELECT * FROM city LIMIT 1")
    fun getCity(): City

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: City)
}