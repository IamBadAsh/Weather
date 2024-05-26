package com.viewsky.weather.RoomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DBWeatherDao {
    @Insert
    fun insert(weatherData: DBWeatherData)

    @Query("SELECT * FROM DBWeatherData")
    fun getAllWeatherData(): List<DBWeatherData>

    @Query("DELETE FROM DBWeatherData")
    suspend fun deleteAllWeatherData()
}
