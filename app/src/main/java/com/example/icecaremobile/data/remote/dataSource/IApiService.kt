package com.example.icecaremobile.data.remote.dataSource

import com.example.icecaremobile.domain.model.Request.LoginRequest
import com.example.icecaremobile.domain.model.Response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IApiService
{
    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

//    @GET("data/2.5/weather")
//    suspend fun getWeather(
//        @Query("q") cityName: String,
//        @Query("appid") apiKey: String = "a224befbe0282d095bb3e06e0cd2b568"
//    ): WeatherDataResponse
}

