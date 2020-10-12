package io.devnido.pokedex.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import io.devnido.pokedex.domain.entities.Pokemon
import io.devnido.pokedex.domain.usecases.GetPokemon
import io.devnido.pokedex.domain.usecases.GetPokemons
import io.devnido.pokedex.core.Result
import kotlinx.coroutines.Dispatchers

class PokemonViewModel(
        private val getPokemons: GetPokemons,
        private val getPokemon: GetPokemon
    ) : ViewModel() {

    private var pokemon = MutableLiveData<Pokemon>()

    fun getPokemonList() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(getPokemons()))
        }catch (e: Exception){
            emit(Result.Error<Exception>(e))
        }

    }

    fun getPokemonDetail(number: Int) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            val wea = getPokemon(number)
            Log.d("TAG_POKEMON_VM", wea.toString())
            emit(Result.Success(wea))
        }catch (e: Exception){
            emit(Result.Error<Exception>(e))
        }
    }

}