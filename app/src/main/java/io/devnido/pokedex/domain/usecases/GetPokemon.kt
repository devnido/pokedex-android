package io.devnido.pokedex.domain.usecases

import io.devnido.pokedex.domain.repository.PokemonRepository
import io.devnido.pokedex.domain.entities.Pokemon

class GetPokemon(private val pokemonRepository: PokemonRepository) {
    suspend operator fun invoke(number:Int): Pokemon = pokemonRepository.getPokemonDetail(number)
}