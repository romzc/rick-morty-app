package com.romzc.app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.romzc.app.databinding.FragmentDetailBinding
import com.romzc.app.model.RickAndMortyCharacter
import com.romzc.app.service.ApiService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class DetailFragment : Fragment() {
    private var userId: Int = 0
    private lateinit var binding: FragmentDetailBinding
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getInt(ARG_USER_ID)
        }
        retrofit = getRetrofit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        getCharacterData(userId)
        return binding.root
    }

    private fun getCharacterData(characterId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: Response<RickAndMortyCharacter> =
                    retrofit.create(ApiService::class.java).getParticularCharacter(characterId)

                if (!response.isSuccessful) {
                    throw Exception()
                }

                val resp: RickAndMortyCharacter = response.body() ?: throw Exception()
                requireActivity().runOnUiThread { updateUI(resp) }

            } catch (e: Exception) {
                Log.i("RICK", "ERROR $e")
            }
        }
    }

    private fun updateUI(response: RickAndMortyCharacter) {
        Picasso.get().load(response.characterImage).into(binding.ivCharacterDetail)
        binding.tvCharacterNameDetail.text = response.characterName
        binding.tvCharacterGenderDetail.text = response.characterGender
        binding.tvCharacterSpecieDetail.text = response.characterSpecies
        binding.tvCharacterStatusDetail.text = response.characterStatus
        binding.tvCharacterLocationDetail.text = response.characterLocation.extraName
        binding.tvCharacterOriginDetail.text = response.characterOrigin.extraName
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://rickandmortyapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {

        const val ARG_USER_ID = "param1"

        @JvmStatic
        fun newInstance(userId: Int) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_USER_ID, userId)
                }
            }

    }
}