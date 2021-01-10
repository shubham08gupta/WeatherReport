package com.weather.report.ui.list

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.weather.report.R
import com.weather.report.databinding.FragmentWeatherListBinding
import com.weather.report.domain.Location
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * A fragment representing a list of weather items.
 */
@AndroidEntryPoint
class WeatherListFragment : Fragment(R.layout.fragment_weather_list), LocationSelectedListener {

    private val viewModel: WeatherListViewModel by viewModels()
    private val listAdapter: WeatherListAdapter = WeatherListAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentWeatherListBinding.bind(view)
        binding.list.apply {
            adapter = listAdapter
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                if (!query.isNullOrBlank()) {
                    navigateToDetailsScreen(query.trim())
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.allLocationsWithWeather.collect {
                listAdapter.submitList(it)
            }
        }
    }

    override fun onLocationSelected(location: Location) {
        navigateToDetailsScreen(location.name)
    }

    private fun navigateToDetailsScreen(city: String) {
        findNavController().navigate(
            WeatherListFragmentDirections.actionWeatherFragmentToWeatherDetailFragment(city)
        )
    }
}