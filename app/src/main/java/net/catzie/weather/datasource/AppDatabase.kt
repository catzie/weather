package net.catzie.weather.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import net.catzie.weather.datasource.weather.WeatherHistoryDao
import net.catzie.weather.model.weather.WeatherHistoryEntity

@Database(entities = [WeatherHistoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherHistoryDao(): WeatherHistoryDao
}