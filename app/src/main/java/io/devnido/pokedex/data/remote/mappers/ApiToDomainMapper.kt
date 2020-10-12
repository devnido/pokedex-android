package io.devnido.pokedex.data.remote.mappers

import io.devnido.pokedex.data.remote.PokeApiService
import io.devnido.pokedex.data.remote.models.PokemonDetailResponse
import io.devnido.pokedex.data.remote.models.PokemonListResponse
import io.devnido.pokedex.domain.entities.Images
import io.devnido.pokedex.domain.entities.Pokemon
import io.devnido.pokedex.domain.entities.Types

class ApiToDomainMapper {

    fun mapPokemonDetailResponseToDomain(pokemonDetailResponse: PokemonDetailResponse): Pokemon {
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

    fun mapPokemonListResponseToDomain(pokemonListResponse: PokemonListResponse): MutableList<Pokemon> {

        val pokemonList = mutableListOf<Pokemon>()

        val spritesBaseUrl = PokeApiService.POKEAPI_SPRITES_BASE_URL
        val imgLargeBaseUrl = PokeApiService.POKEMON_IMG_DB_BASE_URL

        pokemonListResponse.results.map { item ->
            val number =
                item.url.substring(item.url.lastIndexOf("n") + 2, item.url.lastIndexOf("/")).toInt()

            val pokemon = Pokemon(
                name = item.name,
                number = number,
                images = Images(
                    defaultFront = "${spritesBaseUrl}${number}.png",
                    large = "${imgLargeBaseUrl}${item.name}.jpg"
                )
            )

            pokemonList.add(pokemon)
        }

        return pokemonList
    }
}