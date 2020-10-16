package io.devnido.pokedex.data.local.mappers

import io.devnido.pokedex.data.local.models.PokemonEntity
import io.devnido.pokedex.data.remote.PokeApiService
import io.devnido.pokedex.data.remote.models.PokemonDetailResponse
import io.devnido.pokedex.domain.entities.*

class DbToDomainMapper {

    companion object{

        private const val imgLargeBaseUrl = PokeApiService.POKEMON_IMG_DB_BASE_URL

        fun mapPokemonDbToDomain(pokemonEntity: PokemonEntity): Pokemon {

            return Pokemon(
                id = pokemonEntity.id,
                name = pokemonEntity.name,
                order = pokemonEntity.order,
                baseExperience = pokemonEntity.baseExperience,
                height = pokemonEntity.height,
                weight = pokemonEntity.weight,
                images = Images(
                    defaultFront = pokemonEntity.imgSpriteFront,
                    defaultBack = pokemonEntity.imgSpriteBack,
                    shinyFront = pokemonEntity.imgSpriteShinyFront,
                    shinyBack = pokemonEntity.imgSpriteShinyBack,
                    large = "${imgLargeBaseUrl}${pokemonEntity.name}.jpg"
                ),
                types = Types(
                    first = pokemonEntity.firstType,
                    second = pokemonEntity.secondType
                ),
                abilities = pokemonEntity.abilities?.split(",")?.map { Ability(name = it.trim()) },
                stats = pokemonEntity.stats?.split(";")?.map { Stat(name = it.split(",")[0].trim(),base_value = it.split(",")[1].toInt()) }
            )

        }

        fun mapDomainToPokemonDb(pokemon: Pokemon):PokemonEntity{

            return PokemonEntity(
                id = pokemon.id,
                name = pokemon.name,
                order = pokemon.order ?: 0,
                baseExperience = pokemon.baseExperience,
                height = pokemon.height,
                weight = pokemon.weight,
                imgSpriteFront = pokemon.images.defaultFront,
                imgSpriteBack = pokemon.images.defaultBack,
                imgSpriteShinyFront = pokemon.images.shinyFront,
                imgSpriteShinyBack = pokemon.images.shinyBack,
                imgLarge = pokemon.images.large,
                firstType = pokemon.types?.first?:"",
                secondType = pokemon.types?.second,
                abilities = pokemon.abilities?.joinToString(separator = ", ",transform = {ability->ability.name}),
                stats = pokemon.stats?.joinToString (separator = "; ",transform = {stat->"${stat.name},${stat.base_value}"})
            )

        }
    }


}