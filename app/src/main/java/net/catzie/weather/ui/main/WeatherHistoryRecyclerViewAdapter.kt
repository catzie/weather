package net.catzie.weather.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.catzie.weather.Utils.Format.formatTime
import net.catzie.weather.databinding.FragmentWeatherHistoryListItemBinding
import net.catzie.weather.model.weather.WeatherHistoryEntity

class WeatherHistoryRecyclerViewAdapter(
    private var dataSet: List<WeatherHistoryEntity>
) : RecyclerView.Adapter<WeatherHistoryRecyclerViewAdapter.ViewHolder>() {

    private lateinit var binding: FragmentWeatherHistoryListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        binding = FragmentWeatherHistoryListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]

        binding.weatherMain.text = item.weather_main
        binding.weatherDescription.text = item.weather_description
        binding.weatherIcon.text = item.weather_icon
        binding.city.text = item.city
        binding.country.text = item.country
        binding.temp.text = item.temp.toString()
        binding.sunriseTime.text = formatTime(item.sunriseTime)
        binding.sunsetTime.text = formatTime(item.sunsetTime)


    }

    override fun getItemCount(): Int = dataSet.size

    fun updateData(updatedData: List<WeatherHistoryEntity>) {
        dataSet = updatedData
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: FragmentWeatherHistoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

}