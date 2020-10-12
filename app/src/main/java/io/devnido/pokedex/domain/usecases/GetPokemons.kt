package io.devnido.pokedex.domain.usecases

import io.devnido.pokedex.domain.repository.PokemonRepository
import io.devnido.pokedex.domain.entities.Pokemon

class GetPokemons(private val pokemonRepository: PokemonRepository) {
     suspend operator fun invoke(): List<Pokemon> = pokemonRepository.getPokemonList()
}