package com.romzc.app.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.romzc.app.databinding.ItemCharacterBinding
import com.romzc.app.model.RickAndMortyCharacter
import com.squareup.picasso.Picasso

class CharacterViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemCharacterBinding.bind(view)

    fun bind(character: RickAndMortyCharacter) {
        binding.tvCharacterName.text = character.characterName
        binding.tvCharacterStatus.text = character.characterStatus
        Picasso.get().load(character.characterImage).into(binding.ivCharacter)
    }
}