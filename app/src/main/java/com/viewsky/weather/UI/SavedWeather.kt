package com.viewsky.weather.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.viewsky.weather.R
import com.viewsky.weather.RoomDB.DBAppDatabase
import com.viewsky.weather.RoomDB.DBWeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedWeather : AppCompatActivity() {
    private lateinit var clear: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var nodata: TextView
    lateinit var db: DBAppDatabase
    lateinit var  adapter:WeatherDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_saved_weather)
        recyclerView = findViewById(R.id.retreaveView)
        clear = findViewById(R.id.clear)
        nodata = findViewById(R.id.nodata)

     /*   val layoutManager = GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager*/
        val layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        db = Room.databaseBuilder(
            applicationContext,
            DBAppDatabase::class.java, "weather-database"
        ).build()


        GlobalScope.launch(Dispatchers.Main) {

            try {
                val weatherDataList = withContext(Dispatchers.IO) {
                    db.weatherDao().getAllWeatherData()
                }
                Log.d("LogValue",weatherDataList.get(0).windSpeed.toString()+"--"+weatherDataList.get(0).humidity.toString())
                adapter = WeatherDataAdapter(weatherDataList)
                recyclerView.adapter = adapter
                recyclerView.visibility= View.VISIBLE
                nodata.visibility= View.GONE

            }
            catch (e: Exception) {
                nodata.visibility= View.VISIBLE
                recyclerView.visibility= View.GONE

                e.printStackTrace()
            }
        }

        clear.setOnClickListener {
            clearAllDataAndStartActivity()
        }
    }



    private fun clearAllDataAndStartActivity() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                db.weatherDao().deleteAllWeatherData()
                val intent = Intent(baseContext, MainActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {


                e.printStackTrace()
            }
        }
    }
}