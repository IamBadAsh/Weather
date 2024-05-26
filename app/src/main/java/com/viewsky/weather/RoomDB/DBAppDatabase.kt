package com.viewsky.weather.RoomDB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DBWeatherData::class], version = 1)
abstract class DBAppDatabase : RoomDatabase() {
    abstract fun weatherDao(): DBWeatherDao
}
