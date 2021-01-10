package com.weather.report.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.weather.report.databinding.ItemWeatherParametersBinding

class WeatherParamsAdapter : ListAdapter<WeatherParameter, WeatherParamsAdapter.ParamViewHolder>(
    object : DiffUtil.ItemCallback<WeatherParameter>() {
        override fun areItemsTheSame(
            oldItem: WeatherParameter,
            newItem: WeatherParameter
        ): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: WeatherParameter,
            newItem: WeatherParameter
        ): Boolean =
            oldItem == newItem

    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParamViewHolder {
        val view = ItemWeatherParametersBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ParamViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParamViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)
        holder.bind(item)
    }

    inner class ParamViewHolder(private val binding: ItemWeatherParametersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WeatherParameter) {
            binding.icon.setImageDrawable(
                ContextCompat.getDrawable(binding.root.context, item.icon)
            )
            binding.paramName.text = item.paramName
            binding.paramValue.text = item.paramValue
        }
    }
}