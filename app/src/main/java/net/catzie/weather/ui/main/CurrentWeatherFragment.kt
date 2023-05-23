package net.catzie.weather.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import net.catzie.weather.BuildConfig
import net.catzie.weather.R
import net.catzie.weather.Utils.Format.formatTime
import net.catzie.weather.databinding.FragmentCurrentWeatherBinding
import net.catzie.weather.model.ApiResult
import net.catzie.weather.model.weather.WeatherResponse

class CurrentWeatherFragment : Fragment() {

    private lateinit var binding: FragmentCurrentWeatherBinding

    val viewModel: MainViewModel by activityViewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)

        setUpObservers()

        return binding.root
    }

    private fun setUpObservers() {
        viewModel.weather.observe(viewLifecycleOwner, ::displayCurrentWeather)
    }

    private fun displayCurrentWeather(apiResult: ApiResult<WeatherResponse>?) {

        when (apiResult) {

            is ApiResult.Error -> {

                // Display error message
                val toastMessage = "Error: " + getString(apiResult.errorResId)
                Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()
            }

            is ApiResult.Success -> {

                // Display success message
                val toastMessage = getString(R.string.current_weather_res_success)
                Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()

                // Display data
                displayWeatherData(apiResult.data)
            }

            else -> {}
        }
    }

    private fun displayWeatherData(data: WeatherResponse) {
        binding.tvLocation.text =
            getString(R.string.formatted_location_data, data.name, data.sys.country)
        binding.ivIcon.load(
            getString(
                R.string.formatted_weather_icon_url,
                BuildConfig.ICONS_BASE_URL,
                data.weather.first().icon
            )
        )
        binding.tvTemp.text =
            getString(R.string.formatted_temperature_data, data.main.temp.toString())
        binding.tvName.text = getString(
            R.string.formatted_weather_name,
            data.weather.first().main,
            data.weather.first().description
        )
        binding.tvSunrise.text = formatTime(data.sys.sunrise * 1000)
        binding.tvSunset.text = formatTime(data.sys.sunset * 1000)
        binding.tvSunriseLabel.text = getString(R.string.sunrise)
        binding.tvSunsetLabel.text = getString(R.string.sunset)
    }
}