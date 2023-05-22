package net.catzie.weather.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import net.catzie.weather.databinding.FragmentWeatherHistoryBinding
import net.catzie.weather.model.ApiResult
import net.catzie.weather.model.weather.WeatherHistoryEntity
import net.catzie.weather.ui.main.placeholder.PlaceholderContent
import timber.log.Timber

/**
 * A fragment representing a list of Items.
 */
class WeatherHistoryFragment : Fragment() {
    private lateinit var binding: FragmentWeatherHistoryBinding

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

        // Set the adapter
        setUpRecyclerView()
        setUpObservers()

        return binding.root
    }

    private fun setUpRecyclerView() {

        with(binding.rvWeatherHistory) {

            layoutManager =
                if (columnCount <= 1) LinearLayoutManager(context)
                else GridLayoutManager(context, columnCount)

            adapter = WeatherHistoryRecyclerViewAdapter(PlaceholderContent.ITEMS)
        }
    }

    private fun setUpObservers() {
        viewModel.history.observe(viewLifecycleOwner, ::displayWeatherHistory)
    }

    private fun displayWeatherHistory(apiResult: ApiResult<List<WeatherHistoryEntity>>?) {
        Timber.e("historyfragment: $apiResult")
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            WeatherHistoryFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}