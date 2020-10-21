package io.devnido.pokedex.ui.viewmodels

import android.util.Log
import androidx.lifecycle.*
import io.devnido.pokedex.domain.usecases.GetPokemonUseCase
import io.devnido.pokedex.domain.usecases.GetPokemonsUseCase
import io.devnido.pokedex.core.Result
import io.devnido.pokedex.domain.entities.Pokemon
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton


class PokemonViewModel @Inject constructor(
    private val getPokemonsUseCase: GetPokemonsUseCase,
    private val getPokemonUseCase: GetPokemonUseCase
    ) : ViewModel() {

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    private val _selectedPokemon = MutableLiveData<Pokemon>()
    private val _loading = MutableLiveData(true)
    private val _errorMessage = MutableLiveData<String>()

    val pokemonList: LiveData<List<Pokemon>> get() = _pokemonList
    val selectedPokemon: LiveData<Pokemon> get() = _selectedPokemon
    val loading: LiveData<Boolean> get() = _loading
    val errorMessage: LiveData<String> get() = _errorMessage


    fun initPokemonList(){
        viewModelScope.launch {
            _loading.value = true
            withContext(Dispatchers.IO){
                try {
                    _pokemonList.postValue(getPokemonsUseCase())
                }catch (e: Exception){
                    _errorMessage.postValue(e.message)
                }finally {
                    _loading.postValue(false)
                }
            }
        }
    }

    fun getPokemonDetail(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            withContext(Dispatchers.IO){
                try {
                    val pokemon = getPokemonUseCase(id)
                    _selectedPokemon.postValue(pokemon)
                }catch (e: Exception){
                    _errorMessage.postValue(e.message)
                }finally {
                    _loading.postValue(false)
                }
            }
        }

    }


}