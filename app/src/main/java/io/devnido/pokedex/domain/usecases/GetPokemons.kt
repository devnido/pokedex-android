package io.devnido.pokedex.domain.usecases

import io.devnido.pokedex.domain.repository.PokemonRepository
import io.devnido.pokedex.domain.entities.Pokemon
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPokemons @Inject constructor(private val pokemonRepository: PokemonRepository) {
     suspend operator fun invoke(): List<Pokemon> = pokemonRepository.getPokemonList()
}