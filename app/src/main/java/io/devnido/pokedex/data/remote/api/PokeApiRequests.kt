package io.devnido.pokedex.data.remote.api

import android.util.Log
import io.devnido.pokedex.data.remote.PokeApiService
import io.devnido.pokedex.data.remote.models.PokemonDetailResponse
import io.devnido.pokedex.data.remote.models.PokemonListResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokeApiRequests @Inject constructor(private val pokeApiService: PokeApiService) {

    suspend fun getPokemonList(limit:Int,offset:Int): PokemonListResponse {

        val response = pokeApiService.getPokemonList(limit,offset)

        // TODO: validar que el request fue exitoso, en caso contrario lanzar un error personalizado

        return response.body()!!
    }

    suspend fun getPokemon(number:Int): PokemonDetailResponse {

        val response = pokeApiService.getPokemonDetail(number)

        // TODO: validar que el request fue exitoso, en caso contrario lanzar un error personalizado

        return response.body()!!
    }


}