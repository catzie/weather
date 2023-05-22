package net.catzie.weather.datasource.weather

import net.catzie.weather.model.weather.WeatherHistoryEntity

class WeatherHistoryRepository(private val dao: WeatherHistoryDao) {
    suspend fun get(): List<WeatherHistoryEntity> {
        return dao.getAll()

    }

    suspend fun insert(weatherHistoryEntity: WeatherHistoryEntity): Long {
        return dao.insertAll(weatherHistoryEntity)
    }
}