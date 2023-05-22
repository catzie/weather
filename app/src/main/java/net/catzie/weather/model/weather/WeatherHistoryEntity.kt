package net.catzie.weather.model.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_history")
data class WeatherHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "weather_main") val weather_main: String,
    @ColumnInfo(name = "weather_description") val weather_description: String,
    @ColumnInfo(name = "weather_icon") val weather_icon: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "temp") val temp: Double,
    @ColumnInfo(name = "sunrise_time") val sunriseTime: Long,
    @ColumnInfo(name = "sunset_time") val sunsetTime: Long,

    )
