package com.mommysskier.citywheather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import okhttp3.*
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity() {

    private var user_field: EditText? = null
    private var main_btn: Button? = null
    private var result_info: TextView? = null

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        user_field = findViewById(R.id.user_field)
        main_btn = findViewById(R.id.main_btn)
        result_info = findViewById(R.id.result_info)

        main_btn?.setOnClickListener {
            if (user_field?.text?.toString()?.trim()?.equals("")!!)
                Toast.makeText(this, "Введите город", Toast.LENGTH_LONG).show()
            else {

                var city: String = user_field?.text.toString()
                var key: String = "e4ce96df99981362867d24ffceaab53d"
                var url: String = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$key&units=metric&lang=ru"


                val detailIntent = Intent(this, SecondActivity::class.java)
                doAsync {
                    try {
                        var apiResponse = URL(url).readText()
                        result_info?.text = ""
                        Log.d("INFO", JSONObject(apiResponse).getString("cod"))
                        Log.d("INFO", "Город найден")

                        val weather = JSONObject(apiResponse).getJSONArray("weather")
                        val desc = weather.getJSONObject(0).getString("description")

                        val main = JSONObject(apiResponse).getJSONObject("main")
                        val temp = main.getString("temp")

                        detailIntent.putExtra(SecondActivity.API_RESPONS, apiResponse)
                        startActivity(detailIntent)
                    } catch (t: Throwable) {
                        result_info?.text = "Город не найден"
                        Log.d("INFO", "Город не найден")
                        Log.e("ERROR", t.message, t)
                    }
                }
            }
        }
    }
}