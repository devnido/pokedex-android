package io.devnido.pokedex.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.devnido.pokedex.domain.usecases.GetPokemon
import io.devnido.pokedex.domain.usecases.GetPokemons
@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val getPokemons: GetPokemons,private val getPokemon: GetPokemon ): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = PokemonViewModel(getPokemons,getPokemon) as T
}



