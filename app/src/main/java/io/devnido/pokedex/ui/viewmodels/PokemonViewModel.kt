package io.devnido.pokedex.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import io.devnido.pokedex.domain.entities.Pokemon
import io.devnido.pokedex.domain.usecases.GetPokemon
import io.devnido.pokedex.domain.usecases.GetPokemons
import io.devnido.pokedex.core.Result
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonViewModel @Inject constructor(
        private val getPokemons: GetPokemons,
        private val getPokemon: GetPokemon
    ) : ViewModel() {

    fun getPokemonList() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(getPokemons()))
        }catch (e: Exception){
            emit(Result.Error<Exception>(e))
        }

    }

    fun getPokemonDetail(id: Int) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            val pokemon = getPokemon(id)
            emit(Result.Success(pokemon))
        }catch (e: Exception){
            emit(Result.Error<Exception>(e))
        }
    }


}