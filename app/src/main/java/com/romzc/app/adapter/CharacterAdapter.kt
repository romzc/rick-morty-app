package com.romzc.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.romzc.app.R
import com.romzc.app.model.RickAndMortyCharacter

class CharacterAdapter(
    var characterList: List<RickAndMortyCharacter> = emptyList(),
    private val changeToDetail: (Int) -> Unit
) : RecyclerView.Adapter<CharacterViewHolder>() {

    fun updateList(newCharacters: List<RickAndMortyCharacter>) {
        characterList = newCharacters
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        )
    }

    override fun getItemCount(): Int = characterList.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characterList[position])
        holder.itemView.setOnClickListener {
            changeToDetail(characterList[position].characterId)
        }
    }
}