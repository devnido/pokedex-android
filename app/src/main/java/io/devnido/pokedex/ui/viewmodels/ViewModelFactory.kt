package io.devnido.pokedex.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.devnido.pokedex.data.PokemonRepository

class ViewModelFactory(private val pokemonRepository: PokemonRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PokemonRepository::class.java).newInstance(pokemonRepository)
    }
}