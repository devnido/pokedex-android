package io.devnido.pokedex.data.remote.mappers

import io.devnido.pokedex.data.remote.PokeApiService
import io.devnido.pokedex.data.remote.models.PokemonDetailResponse
import io.devnido.pokedex.data.remote.models.PokemonListResponse
import io.devnido.pokedex.domain.entities.*

class ApiToDomainMapper {

    companion object{

        private const val spritesBaseUrl = PokeApiService.POKEAPI_SPRITES_BASE_URL
        private const val imgLargeBaseUrl = PokeApiService.POKEMON_IMG_DB_BASE_URL

        fun mapPokemonDetailResponseToDomain(pokemonDetailResponse: PokemonDetailResponse): Pokemon {


            return Pokemon(
                id = pokemonDetailResponse.id,
                name = pokemonDetailResponse.name,
                order = pokemonDetailResponse.order,
                baseExperience = pokemonDetailResponse.baseExperience,
                height = pokemonDetailResponse.height.toFloat()/10,
                weight = pokemonDetailResponse.weight.toFloat()/10,
                images = Images(
                    defaultFront = pokemonDetailResponse.sprites.frontDefault,
                    defaultBack = pokemonDetailResponse.sprites.backDefault,
                    shinyFront = pokemonDetailResponse.sprites.frontShiny,
                    shinyBack = pokemonDetailResponse.sprites.backShiny,
                    large = "${imgLargeBaseUrl}${pokemonDetailResponse.name}.jpg"
                ),
                types = Types(
                    first = pokemonDetailResponse.types.getOrNull(0)?.type?.name,
                    second = pokemonDetailResponse.types.getOrNull(1)?.type?.name
                ),
                abilities = pokemonDetailResponse.abilities.map {
                    Ability(name = it.ability.name)
                },
                stats = pokemonDetailResponse.stats.map {
                    Stat(name = it.stat.name,base_value = it.base)
                }
            )

        }

        fun mapPokemonListResponseToDomain(pokemonListResponse: PokemonListResponse): MutableList<Pokemon> {

            val pokemonList = mutableListOf<Pokemon>()

            pokemonListResponse.results.map { item ->
                val id =
                    item.url.substring(item.url.lastIndexOf("n") + 2, item.url.lastIndexOf("/")).toInt()

                val pokemon = Pokemon(
                    name = item.name,
                    id = id,
                    images = Images(
                        defaultFront = "${spritesBaseUrl}${id}.png",
                        large = "${imgLargeBaseUrl}${item.name}.jpg"
                    )
                )

                pokemonList.add(pokemon)
            }

            return pokemonList
        }

    }


}