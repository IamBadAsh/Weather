package com.viewsky.weather.UI

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.viewsky.weather.R

class WeatherAdapter(private var dates: List<String>, private var temperatures: List<Double>, private var windSpeedValue: List<Double>, private var humidityValue: List<Int>, private var rainValue: List<Int>,private val listener: ButtonClickListener) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    interface ButtonClickListener {
        fun onButtonClick(position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dates[position]
            , temperatures[position]
            , windSpeedValue[position]
            , humidityValue[position]
            , rainValue[position]

        )
        holder.button.setOnClickListener {
            listener.onButtonClick(position)
        }
    }

    override fun getItemCount(): Int {
        return dates.size
    }

    fun updateData(
        dates: List<String>,
        temperatures: List<Double>,
        windSpeedValue: List<Double>,
        humidityValue: List<Int>,
        rainValue: List<Int>
    ) {
        this.dates = dates
        this.temperatures = temperatures
        this.windSpeedValue=windSpeedValue
        this.humidityValue=humidityValue
        this.rainValue=rainValue
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val button: Button = itemView.findViewById(R.id.save_db)
        private val dateTextView: TextView = itemView.findViewById(R.id.dateValue)
        private val temperatureTextView: TextView = itemView.findViewById(R.id.temperatureValue)

        private val windSpeedValue: TextView = itemView.findViewById(R.id.windSpeedValue)
        private val humidityValue: TextView = itemView.findViewById(R.id.humidityValue)
        private val rainValue: TextView = itemView.findViewById(R.id.rainValue)

        fun bind(date: String, temperature: Double, windSpeed: Double, humidity: Int, rain: Int) {

            dateTextView.text = date
            temperatureTextView.text = temperature.toString()+"\u00B0"
            windSpeedValue.text = windSpeed.toString()+"Km/hr"
            humidityValue.text = humidity.toString()+"%"
            rainValue.text = rain.toString()+"%"

        }
    }
}
