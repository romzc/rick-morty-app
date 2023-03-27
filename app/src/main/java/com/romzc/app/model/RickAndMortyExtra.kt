package com.romzc.app.model

import com.google.gson.annotations.SerializedName

data class RickAndMortyExtra(
    @SerializedName("name") val extraName: String,
    @SerializedName("url") val extraUrl: String
)
