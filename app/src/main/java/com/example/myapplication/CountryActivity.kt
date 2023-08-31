package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CountryActivity : AppCompatActivity() {

    private val apiService = RetrofitClient.apiService
    private lateinit var recyclerView: RecyclerView
    private lateinit var countryAdapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        runBlocking {
                try {
                    getCountryData()
                } catch (e: Exception) {
                }
        }
    }
    suspend fun getCountryData(){

        CoroutineScope(Dispatchers.Main).launch(Dispatchers.Main) {
            try {
                val response = apiService.getCountry()
                if (response.isSuccessful && response.body() != null) {
                     val country = response.body()!!
                     countryAdapter = CountryAdapter(response.body()!!)
                    recyclerView.adapter = countryAdapter
                    for ((index, CountryModel) in country.withIndex()) {
                        ///print all country name
                        Log.e("Api res : ", "country name : $index :  ${CountryModel.name}")
                    }
                } else {
                    Toast.makeText(
                        this@CountryActivity,
                        "Error Occurred: ${response.message()}",
                        Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@CountryActivity,
                    "Error Occurred: ${e.message}",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

}