package io.devnido.pokedex.domain.usecases

import io.devnido.pokedex.domain.repository.PokemonRepository
import io.devnido.pokedex.domain.entities.Pokemon
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPokemonUseCase @Inject constructor(private val pokemonRepository: PokemonRepository) {
    suspend operator fun invoke(id:Int): Pokemon = pokemonRepository.getPokemonDetail(id)
}