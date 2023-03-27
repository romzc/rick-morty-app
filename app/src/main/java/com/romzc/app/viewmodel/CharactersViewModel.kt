package com.romzc.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.romzc.app.model.RickAndMortyCharacter
import com.romzc.app.model.RickAndMortyResponse
import kotlinx.coroutines.flow.MutableStateFlow

class CharactersViewModel: ViewModel() {
    private val _characters = MutableLiveData<RickAndMortyResponse>()
    val characters: MutableLiveData<RickAndMortyResponse> = _characters

    init {
        _characters.value = RickAndMortyResponse()
    }

    fun updateRickAndMorty(newResponse: RickAndMortyResponse) {
        _characters.value = newResponse
    }
}