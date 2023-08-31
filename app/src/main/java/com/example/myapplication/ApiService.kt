package com.example.myapplication

import com.example.myapplication.model.CountryModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("countries.json")
    suspend fun getCountry() : Response<CountryModel>

}

