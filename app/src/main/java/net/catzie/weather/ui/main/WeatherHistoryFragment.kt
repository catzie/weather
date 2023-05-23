package net.catzie.weather.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import net.catzie.weather.R
import net.catzie.weather.databinding.FragmentWeatherHistoryBinding
import net.catzie.weather.model.ApiResult
import net.catzie.weather.model.weather.WeatherHistoryEntity
import timber.log.Timber

class WeatherHistoryFragment : Fragment() {

    private lateinit var binding: FragmentWeatherHistoryBinding
    val weatherHistoryAdapter = WeatherHistoryRecyclerViewAdapter(listOf())

    private var columnCount = 1

    val viewModel: MainViewModel by activityViewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        // Let ViewModel know that we need weather history
        viewModel.onWeatherHistoryFragmentCreated()

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentWeatherHistoryBinding.inflate(inflater, container, false)

        setUpRecyclerView()
        setUpObservers()

        return binding.root
    }

    private fun setUpRecyclerView() {

        with(binding.rvWeatherHistory) {

            layoutManager =
                if (columnCount <= 1) LinearLayoutManager(context)
                else GridLayoutManager(context, columnCount)

            adapter = weatherHistoryAdapter
        }
    }

    private fun setUpObservers() {
        viewModel.history.observe(viewLifecycleOwner, ::displayWeatherHistory)
    }

    private fun displayWeatherHistory(apiResult: ApiResult<List<WeatherHistoryEntity>>?) {

        when (apiResult) {

            is ApiResult.Error -> {

                // Display error message
                val toastMessage = "Error: " + getString(apiResult.errorResId)
                Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()
            }

            is ApiResult.Success -> {

                Timber.d(getString(R.string.weather_history_res_success))

                // Update history
                weatherHistoryAdapter.updateData(apiResult.data)
            }

            else -> {}
        }

    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
    }
}