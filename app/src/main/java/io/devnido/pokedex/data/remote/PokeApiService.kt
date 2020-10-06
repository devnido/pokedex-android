package io.devnido.pokedex.data.remote

import io.devnido.pokedex.data.remote.models.PokemonDetailResponse
import io.devnido.pokedex.data.remote.models.PokemonListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit:Int,@Query("offset'") offset:Int): Response<PokemonListResponse>

    @GET("pokemon")
    suspend fun getPokemonDetail(@Path("number") number:Int): Response<PokemonDetailResponse>

    companion object{
        const val POKEAPI_BASE_URL = "https://pokeapi.co/api/v2/"
    }


}