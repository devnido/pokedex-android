package io.devnido.pokedex.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.devnido.pokedex.R
import io.devnido.pokedex.core.BaseViewHolder
import io.devnido.pokedex.domain.entities.Pokemon
import kotlinx.android.synthetic.main.pokemon_card.view.*

class PokemonListAdapter(
    private val context: Context,
    private val pokemonList: List<Pokemon>,
    private val itemClickListener: OnPokemonClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>()  {

    interface OnPokemonClickListener {
        fun onPokemonClick(pokemon: Pokemon,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MainViewHolder(
            LayoutInflater.from(context).inflate(R.layout.pokemon_card, parent, false)
        )    }

    override fun getItemCount(): Int = pokemonList.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(pokemonList[position], position)
        }
    }


    inner class MainViewHolder(itemView: View) : BaseViewHolder<Pokemon>(itemView) {
        override fun bind(item: Pokemon, position: Int) {
            Glide.with(context).load(item.images.defaultFront).centerCrop().into(itemView.imgPokemon)
            itemView.namePokemon.text = item.name
            itemView.setOnClickListener { itemClickListener.onPokemonClick(item,position) }
        }
    }
}