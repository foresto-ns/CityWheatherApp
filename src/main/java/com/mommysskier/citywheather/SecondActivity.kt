package com.mommysskier.citywheather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import org.json.JSONObject

class SecondActivity : AppCompatActivity() {

    private var city: TextView? = null
    private var textview: TextView? = null

    companion object{
        const val API_RESPONS  = "api"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        city = findViewById(R.id.City)
        textview = findViewById(R.id.textViewDetails)
        showdetails()


    }

    fun showdetails(){
        var apiResponse = intent.getStringExtra(API_RESPONS)

        val wheatherCity = JSONObject(apiResponse).getString("name")
        val weather = JSONObject(apiResponse).getJSONArray("weather")
        val desc = weather.getJSONObject(0).getString("description")

        val main = JSONObject(apiResponse).getJSONObject("main")
        val temp = main.getString("temp")

        city?.text = "$wheatherCity"
        textview?.text = "Температура: $temp\n$desc\n"
    }
}