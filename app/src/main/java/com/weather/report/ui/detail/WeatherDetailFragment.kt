package com.weather.report.ui.detail

import android.os.Bundle
import android.text.format.DateUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.weather.report.R
import com.weather.report.databinding.FragmentWeatherDetailBinding
import com.weather.report.domain.Location
import com.weather.report.ui.getDisplayMessage
import com.weather.report.util.DummyData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherDetailFragment : Fragment(R.layout.fragment_weather_detail) {

    private val args: WeatherDetailFragmentArgs by navArgs()
    private val viewModel: WeatherDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentWeatherDetailBinding.bind(view)
        viewModel.getWeatherForecastFor(args.city)
        viewModel.data.observe(viewLifecycleOwner) { list ->
            showDetailsOnUI(binding, list)
        }
        viewModel.errorType.observe(viewLifecycleOwner) { errorType ->
            context?.let {
                Toast.makeText(it, errorType.getDisplayMessage(it), Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun showDetailsOnUI(
        binding: FragmentWeatherDetailBinding,
        list: List<Location>
    ) {
        if (list.isNotEmpty()) {
            showTodayWeatherParams(binding, list.first())
            showLast7DaysWeather(binding, list - list.first())
        }
    }

    private fun showTodayWeatherParams(
        binding: FragmentWeatherDetailBinding,
        location: Location
    ) {
        with(location) {
            if (weather == null) return
            binding.ivWeather.load(weather.weatherIcons.firstOrNull())
            binding.tvCityName.text =
                getString(R.string.title_city_country, location.name, location.country)
            binding.tvWeatherDesc.text = weather.weatherDescriptions.first()
            binding.tvCurrentTemp.text =
                getString(R.string.degree_celsius, weather.temperature.toString())
            binding.tvLastUpdatedAt.text =
                DateUtils.getRelativeDateTimeString(
                    binding.root.context,
                    lastUpdatedAt.toEpochSecond().times(1000),
                    DateUtils.MINUTE_IN_MILLIS,
                    DateUtils.WEEK_IN_MILLIS,
                    1
                )
            val paramAdapter = WeatherParamsAdapter()
            val list = listOf(
                WeatherParameter(
                    R.drawable.ic_feels_like,
                    getString(R.string.title_feels_like),
                    getString(R.string.value_feels_like, weather.feelsLike.toString())
                ),
                WeatherParameter(
                    R.drawable.ic_wind,
                    getString(R.string.title_wind_speed),
                    getString(R.string.value_wind_speed, weather.windSpeed.toString())
                ),
                WeatherParameter(
                    R.drawable.ic_humidity,
                    getString(R.string.title_humidity),
                    getString(R.string.value_humidity, weather.humidity.toString())
                ),
                WeatherParameter(
                    R.drawable.ic_compass,
                    getString(R.string.title_pressure),
                    getString(R.string.value_pressure, weather.pressure.toString())
                ),
                WeatherParameter(
                    R.drawable.ic_visibility,
                    getString(R.string.title_visibility),
                    getString(R.string.value_visibility, weather.visibility.toString())
                ),
                WeatherParameter(
                    R.drawable.ic_precipitation,
                    getString(R.string.title_precipitation),
                    getString(R.string.value_precipitation, weather.precipitation.toString())
                )

            )
            binding.rvWeatherParams.apply {
                layoutManager = GridLayoutManager(context, 3)
                adapter = paramAdapter
            }
            paramAdapter.submitList(list)
        }
    }

    private fun showLast7DaysWeather(
        binding: FragmentWeatherDetailBinding,
        list: List<Location>
    ) {
        val displayList = mutableListOf<Location>()
        // fetch dummy data if we do not have data in the DB for the last 7 days
        if (list.size < 7) {
            val dummyList = DummyData.createDummyLocations()
            displayList.addAll(dummyList)
        } else {
            displayList.addAll(list)
        }
        binding.tvLast7Days.visibility = View.VISIBLE
        val last7DaysAdapter = WeatherLast7DaysAdapter()
        binding.rvLast7Days.apply {
            adapter = last7DaysAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        last7DaysAdapter.submitList(displayList)
    }
}