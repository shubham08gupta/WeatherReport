package com.weather.report.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.weather.report.databinding.ItemWeatherLast7DayBinding
import com.weather.report.domain.Location

class WeatherLast7DaysAdapter(
) : ListAdapter<Location, WeatherLast7DaysAdapter.ViewHolder>(
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
        val binding =
            ItemWeatherLast7DayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(holder.bindingAdapterPosition)
        holder.bind(item)
    }

    inner class ViewHolder(
        private val binding: ItemWeatherLast7DayBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(location: Location) {
            with(location) {
                if (weather == null) return
                binding.ivWeather.load(weather.weatherIcons.first())
                binding.tvTemp.text = weather.temperature.toString()
                binding.tvDay.text = lastUpdatedAt.dayOfWeek.name.substring(0, 3)
            }

        }
    }

}