package io.devnido.pokedex.domain.mappers

import io.devnido.pokedex.data.remote.models.PokemonDetailResponse
import io.devnido.pokedex.domain.entities.Images
import io.devnido.pokedex.domain.entities.Pokemon
import io.devnido.pokedex.domain.entities.Types

class DomainMapper {

    fun mapPokemonResponseToDomain(pokemonDetailResponse: PokemonDetailResponse):Pokemon{
        return Pokemon(
            name = pokemonDetailResponse.name,
            number = pokemonDetailResponse.order,
            images = Images(
                defaultFront = pokemonDetailResponse.sprites.frontDefault,
                defaultBack = pokemonDetailResponse.sprites.backDefault,
                shinyFront = pokemonDetailResponse.sprites.frontShiny,
                shinyBack = pokemonDetailResponse.sprites.backShiny
            ),
            types = Types(
                first = pokemonDetailResponse.types[0].type.name,
                second = pokemonDetailResponse.types[1].type.name
            )
        )
    }
}