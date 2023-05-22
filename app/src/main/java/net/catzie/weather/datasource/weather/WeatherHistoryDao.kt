package net.catzie.weather.datasource.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import net.catzie.weather.model.weather.WeatherHistoryEntity

@Dao
interface WeatherHistoryDao {

    @Query("SELECT * FROM weather_history")
    fun getAll(): List<WeatherHistoryEntity>

    @Insert
    fun insertAll(weatherHistoryEntity: WeatherHistoryEntity): Long
}