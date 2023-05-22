package net.catzie.weather.model.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherHistoryEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "weather") val weather: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "temp") val temp: Double,
    @ColumnInfo(name = "sunrise_time") val sunriseTime: Int,
    @ColumnInfo(name = "sunset_time") val sunsetTime: Int,

    )
