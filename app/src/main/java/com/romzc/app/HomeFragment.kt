package com.romzc.app

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.romzc.app.adapter.CharacterAdapter
import com.romzc.app.databinding.FragmentHomeBinding
import com.romzc.app.service.ApiService
import com.romzc.app.model.RickAndMortyResponse
import com.romzc.app.viewmodel.CharactersViewModel
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: CharacterAdapter
    private lateinit var callback: ActivityCallback
    private lateinit var charactersViewModel: CharactersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retrofit = getRetrofit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initUI()
        return binding.root
    }

    private fun initUI() {
        binding.svCharacterLook.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchCharacter(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
        charactersViewModel = ViewModelProvider(this).get(CharactersViewModel::class.java)
        // se envia una funcion lambda que permite cambiar a la vista detalle.
        // de cada personaje de la serie.
        adapter = CharacterAdapter() { id -> changeFragment(id) }
        binding.rvCharacterList.setHasFixedSize(true)
        binding.rvCharacterList.layoutManager = LinearLayoutManager(context)
        binding.rvCharacterList.adapter = adapter

        charactersViewModel.characters.observe(viewLifecycleOwner) { response ->
            adapter.updateList(response.listOfCharacter)
        }
    }

    private fun searchCharacter(name: String) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val characterResponse: Response<RickAndMortyResponse> =
                    retrofit.create(ApiService::class.java).getCharacter(name)

                if (characterResponse.isSuccessful) {
                    val response: RickAndMortyResponse? = characterResponse.body()
                    if (response != null) {
                        // It's require run on ui thread any changes on ui.
                        requireActivity().runOnUiThread {
                            charactersViewModel.updateRickAndMorty(response)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.i("RICK", "ERROR $e")
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun changeFragment(characterId: Int) {
        callback.onLaunchFragmentFromFragment(CHANGE_TO_DETAIL, characterId)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            if (context is ActivityCallback)
                callback = context as ActivityCallback
        } catch (error: Exception) {
            Log.i("RICK", "$error")
        }
    }

    companion object {

        const val CHANGE_TO_DETAIL = "detail"

        @JvmStatic
        fun newInstance() = HomeFragment().apply { arguments = Bundle() }
    }
}