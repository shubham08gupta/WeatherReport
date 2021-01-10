package com.weather.report.ui.list

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.weather.report.R
import com.weather.report.databinding.ItemWeatherBinding
import com.weather.report.domain.Location

class WeatherListAdapter(
    private val listener: LocationSelectedListener
) : ListAdapter<Location, WeatherListAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(
            oldItem: Location,
            newItem: Location
        ): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Location,
            newItem: Location
        ): Boolean =
            oldItem == newItem

    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)
        holder.bind(item)
    }

    inner class ViewHolder(
        private val binding: ItemWeatherBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(location: Location) {
            with(location) {
                val context = binding.root.context
                binding.ivWeather.load(weather?.weatherIcons?.first())
                binding.tvCity.text =
                    context.getString(R.string.title_city_country, name, country)
                binding.tvTemp.text =
                    context.getString(R.string.degree_celsius, weather?.temperature.toString())
                binding.tvWeather.text = weather?.weatherDescriptions?.first()
                binding.tvLastUpdatedAt.text =
                    DateUtils.getRelativeDateTimeString(
                        binding.root.context,
                        lastUpdatedAt.toEpochSecond().times(1000),
                        DateUtils.MINUTE_IN_MILLIS,
                        DateUtils.WEEK_IN_MILLIS,
                        1
                    )
                binding.container.setOnClickListener {
                    listener.onLocationSelected(this)
                }
            }

        }
    }

}