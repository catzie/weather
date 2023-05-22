package net.catzie.weather.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import net.catzie.weather.R
import net.catzie.weather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //todo Remove once you make a necessary call to the viewmodel
        viewModel.testCall()

        setUpTabbedLayout()
    }

    private fun setUpTabbedLayout() {

        //Setup viewpager
        binding.viewPager.adapter = ViewPagerAdapter(this)

        //Setup tablayout
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_current)
                1 -> getString(R.string.tab_history)
                else -> throw IllegalArgumentException("Invalid position: $position")
            }
        }.attach()
    }
}