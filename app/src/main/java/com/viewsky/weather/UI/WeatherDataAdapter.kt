package com.viewsky.weather.UI

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.viewsky.weather.R
import com.viewsky.weather.RoomDB.DBWeatherData

class WeatherDataAdapter(private val weatherDataList: List<DBWeatherData>) :
    RecyclerView.Adapter<WeatherDataAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeTextView: TextView = itemView.findViewById(R.id.dateValue)
        val temperatureTextView: TextView = itemView.findViewById(R.id.temperatureValue)

        val windSpeedValue: TextView = itemView.findViewById(R.id.windSpeedValue)
        val humidityValue: TextView = itemView.findViewById(R.id.humidityValue)
        val rainValue: TextView = itemView.findViewById(R.id.rainValue)
        val save_db: Button = itemView.findViewById(R.id.save_db)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherData = weatherDataList[position]
        holder.timeTextView.text = "${weatherData.time}"
        holder.temperatureTextView.text = "${weatherData.temperature}"+"\u00B0"
        holder.humidityValue.text = "${weatherData.humidity}"+"%"
        holder.windSpeedValue.text = "${weatherData.windSpeed}"+"Km/hr"
        holder.rainValue.text = "${weatherData.precipitation}"+"%"
        holder.save_db.visibility=View.GONE
    }

    override fun getItemCount(): Int {
        return weatherDataList.size
    }
}
