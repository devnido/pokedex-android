package io.devnido.pokedex.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import io.devnido.pokedex.data.PokemonRepository
import io.devnido.pokedex.domain.entities.Pokemon
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MainViewModel(private val pokemonRepository: PokemonRepository):ViewModel() {

    val fetchTragosList = liveData(Dispatchers.IO) {

            emit(pokemonRepository.getPokemonListFake())

    }

}