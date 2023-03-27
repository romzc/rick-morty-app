package com.romzc.app.service

import com.romzc.app.model.RickAndMortyCharacter
import com.romzc.app.model.RickAndMortyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/api/character/")
    suspend fun getCharacter(@Query("name") name: String): Response<RickAndMortyResponse>

    @GET("/api/character/{id}")
    suspend fun getParticularCharacter(@Path("id") id: Int): Response<RickAndMortyCharacter>
}