package io.devnido.pokedex.ui.viewmodels

import android.util.Log
import androidx.lifecycle.*
import io.devnido.pokedex.domain.usecases.GetPokemonUseCase
import io.devnido.pokedex.domain.usecases.GetPokemonsUseCase
import io.devnido.pokedex.core.Result
import io.devnido.pokedex.domain.entities.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonViewModel @Inject constructor(
    private val getPokemonsUseCase: GetPokemonsUseCase,
    private val getPokemonUseCase: GetPokemonUseCase
    ) : ViewModel() {

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    private val _selectedPokemon = MutableLiveData<Pokemon>()
    private val _loading = MutableLiveData<Boolean>()
    private val _errorMessage = MutableLiveData<String>()

    val pokemonList: LiveData<List<Pokemon>> get() = _pokemonList
    val selectedPokemon: LiveData<Pokemon> get() = _selectedPokemon
    val loading: LiveData<Boolean> get() = _loading
    val errorMessage: LiveData<String> get() = _errorMessage

    init {
        initPokemonList()
    }


    private fun initPokemonList(){
        viewModelScope.launch {

            _loading.value = true
            withContext(Dispatchers.IO){
                try {
                    _pokemonList.postValue(getPokemonsUseCase())
                }catch (e: Exception){
                    Log.e("TAG_POKEMON",e.message!!)
                    _errorMessage.postValue(e.message)
                }finally {
                    _loading.postValue(false)
                }
            }

        }
    }



    fun getPokemonDetail(id: Int) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            val pokemon = getPokemonUseCase(id)
            emit(Result.Success(pokemon))
        }catch (e: Exception){
            emit(Result.Error<Exception>(e))
        }
    }


}