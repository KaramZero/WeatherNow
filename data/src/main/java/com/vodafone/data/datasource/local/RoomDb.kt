package com.vodafone.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vodafone.data.model.City


@Database(entities = [City::class], version = 1)
abstract class RoomDb : RoomDatabase() {

    abstract fun cityDao(): CityDao
}