package com.vodafone.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vodafone.data.datasource.local.CityDao
import com.vodafone.data.datasource.local.RoomDb
import com.vodafone.data.model.City
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CityDaoTest {

    private lateinit var database: RoomDb
    private lateinit var cityDao: CityDao

    @Before
    fun setUp() {
        // Create an in-memory Room database
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RoomDb::class.java
        ).build()

        // Initialize the CityDao
        cityDao = database.cityDao()
    }

    @After
    fun tearDown() {
        // Close the database after each test
        database.close()
    }

    @Test
    fun insertCity_shouldInsertCity() = runBlocking {
        // Given
        val city = City(id = 1, name = "Cairo")

        // When
        cityDao.insertCity(city)

        // Then
        val retrievedCity = cityDao.getCity()
        Assert.assertEquals(city, retrievedCity)
    }

    @Test
    fun getCity_shouldReturnCorrectCity() = runBlocking {
        // Given
        val city = City(id = 1, name = "Cairo")
        cityDao.insertCity(city)

        // When
        val retrievedCity = cityDao.getCity()

        // Then
        Assert.assertEquals("Cairo", retrievedCity.name)
    }

    @Test
    fun insertCity_withConflict_shouldReplaceCity() = runBlocking {
        // Given
        val city1 = City(id = 1, name = "Cairo")
        cityDao.insertCity(city1)

        val city2 = City(id = 1, name = "Alexandria")

        // When
        cityDao.insertCity(city2)

        // Then
        val retrievedCity = cityDao.getCity()
        Assert.assertEquals("Alexandria", retrievedCity.name)
    }

    @Test
    fun insertCity_shouldHandleMultipleInserts() = runBlocking {
        // Given
        val city1 = City(id = 1, name = "Cairo")
        val city2 = City(id = 2, name = "Alexandria")

        // When
        cityDao.insertCity(city1)
        cityDao.insertCity(city2)

        // Then
        val retrievedCity1 = cityDao.getCity()
        Assert.assertEquals("Cairo", retrievedCity1.name)
    }
}
