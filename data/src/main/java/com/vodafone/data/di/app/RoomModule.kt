package com.vodafone.data.di.app

import android.content.Context
import androidx.room.Room
import com.vodafone.data.datasource.local.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {


    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        RoomDb::class.java,
        "City"
    ).build()


    @Singleton
    @Provides
    fun provideCityDao(db: RoomDb) = db.cityDao()

}