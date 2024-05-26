package com.viewsky.weather.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.viewsky.weather.R
import com.viewsky.weather.RoomDB.DBAppDatabase
import com.viewsky.weather.RoomDB.DBWeatherData
import com.viewsky.weather.WeatherForecast.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll

class MainActivity : AppCompatActivity(), WeatherAdapter.ButtonClickListener {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var windValue: TextView
    private lateinit var log_act: ImageView
    private lateinit var rainValue: TextView
    private lateinit var humidityValue: TextView
    private lateinit var tempValue: TextView
    private lateinit var adapter: WeatherAdapter
    lateinit var db: DBAppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        windValue = findViewById(R.id.windValue)
        log_act = findViewById(R.id.log_act)
        rainValue = findViewById(R.id.rainValue)
        humidityValue = findViewById(R.id.humidityValue)
        tempValue = findViewById(R.id.tempValue)


        log_act.setOnClickListener {
            val intent = Intent(this, SavedWeather::class.java)
            startActivity(intent)
        }

         db = Room.databaseBuilder(
            applicationContext,
            DBAppDatabase::class.java, "weather-database"
        ).build()


        val layoutManager = GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        adapter = WeatherAdapter(
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
            this
        ) // Initialize with empty lists
        recyclerView.adapter = adapter

        // Observe weather forecast LiveData
        viewModel.weatherForecast.observe(this, Observer { weatherForecast ->
            windValue.text = weatherForecast.daily.wind_speed_10m_max.firstOrNull()?.toString() + "Km/hr"
            rainValue.text = weatherForecast.daily.precipitation_probability_mean.firstOrNull()?.toString() + "%"
            humidityValue.text = weatherForecast.daily.relative_humidity_2m_max.firstOrNull()?.toString() + "%"
            tempValue.text = weatherForecast.daily.temperature_2m_max.firstOrNull()?.toString() + "\u00B0"
            adapter.updateData(
                weatherForecast.daily.time,
                weatherForecast.daily.temperature_2m_max,
                weatherForecast.daily.wind_speed_10m_max,
                weatherForecast.daily.relative_humidity_2m_max,
                weatherForecast.daily.precipitation_probability_mean
            )
        })
        viewModel.fetchWeatherForecast(52.52, 13.41)




        val autoCompleteTextView: AutoCompleteTextView = findViewById(R.id.autoCompleteTextView)
        val cityNames = cities.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, cityNames)
        autoCompleteTextView.setAdapter(adapter)



        autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val selectedCityName = parent.adapter.getItem(position) as String

            val selectedCity = cities.firstOrNull { it.name == selectedCityName }
            if (selectedCity != null) {
                val latitude = selectedCity.latitude
                val longitude = selectedCity.longitude
                // Use latitude and longitude as needed

                Log.d("LatLang",selectedCity.name+"-- $position"+"latitude$latitude"+"  longitude$longitude")
                viewModel.fetchWeatherForecast(selectedCity.latitude, selectedCity.longitude)
            }
        }


  /*      autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            val selectedCity = cities[position]

            val latitude = selectedCity.latitude
            val longitude = selectedCity.longitude

            Log.d("LatLang",selectedCity.name+"-- $position"+"latitude$latitude"+"  longitude$longitude")
*//*
            viewModel.fetchWeatherForecast(selectedCity.latitude, selectedCity.longitude)
*//*
        }*/
    }
    override fun onButtonClick(position: Int) {
        kotlin.run {
            viewModel.weatherForecast.observe(this, Observer { weatherForecast ->
                val weatherData =   DBWeatherData(  time=weatherForecast.daily.time.get(position),
                    windSpeed = weatherForecast.daily.wind_speed_10m_max.get(position).toFloat(),
                    precipitation= weatherForecast.daily.precipitation_probability_mean.get(position).toFloat(),
                    humidity = weatherForecast.daily.relative_humidity_2m_max.get(position).toFloat(),
                    temperature= weatherForecast.daily.temperature_2m_max.get(position).toFloat())

                GlobalScope.launch(Dispatchers.IO) {
                    db.weatherDao().insert(weatherData)
                }
            })
        }
    }
}

