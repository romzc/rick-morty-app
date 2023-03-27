package com.romzc.app.model


import com.google.gson.annotations.SerializedName

data class RickAndMortyCharacter(
    @SerializedName("id") val characterId: Int,
    @SerializedName("name") val characterName: String,
    @SerializedName("status") val characterStatus: String,
    @SerializedName("species") val characterSpecies: String,
    @SerializedName("type") val characterType: String,
    @SerializedName("gender") val characterGender: String,
    @SerializedName("image") val characterImage: String,
    @SerializedName("url") val characterDetail: String,
    @SerializedName("origin") val characterOrigin: RickAndMortyExtra,
    @SerializedName("location") val characterLocation: RickAndMortyExtra
)
