package io.devnido.pokedex.domain.repository

import io.devnido.pokedex.domain.entities.Pokemon

interface PokemonRepository {

    suspend fun getPokemonList():List<Pokemon>

    suspend fun getPokemonDetail(number:Int):Pokemon



}