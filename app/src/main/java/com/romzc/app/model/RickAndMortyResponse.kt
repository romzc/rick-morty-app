package com.romzc.app.model
import com.google.gson.annotations.SerializedName



data class RickAndMortyResponse (
    @SerializedName("info") val info: ResponseInfo? = null,
    @SerializedName("results") val listOfCharacter: List<RickAndMortyCharacter> = emptyList()
)