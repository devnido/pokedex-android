package io.devnido.pokedex.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.devnido.pokedex.databinding.PokemonCardBinding
import io.devnido.pokedex.domain.entities.Pokemon

class PokemonListAdapter(
    private val pokemonList: List<Pokemon>,
    private val itemClickListener: OnPokemonClickListener
) : RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>()  {

    interface OnPokemonClickListener {
        fun onPokemonClick(pokemon: Pokemon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: PokemonCardBinding =
            PokemonCardBinding.inflate(layoutInflater, parent, false)
        return PokemonViewHolder( binding )

    }

    override fun getItemCount(): Int = pokemonList.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {

        holder.pokemonCardBinding.pokemon = pokemonList[position]
        holder.pokemonCardBinding.pokemonClickListener = itemClickListener
        holder.pokemonCardBinding.executePendingBindings()

    }


    inner class PokemonViewHolder(val pokemonCardBinding: PokemonCardBinding) : RecyclerView.ViewHolder(pokemonCardBinding.root)





}