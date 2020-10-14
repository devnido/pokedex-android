package io.devnido.pokedex.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
    var id: Int,
    var order: Int? = 0,
    var name: String,
    var baseExperience: Int? = 0,
    var weight: Float? = 0f,
    var height: Float? = 0f,
    var images: Images,
    var types: Types? = null,
    var abilities: List<Ability>? = null
): Parcelable

@Parcelize
data class Images(
    var defaultFront:String,
    var defaultBack:String = "",
    var shinyFront:String = "",
    var shinyBack:String = "",
    var large:String = ""
): Parcelable

@Parcelize
data class Types(
    var first:String? = "",
    var second:String? = ""
): Parcelable


@Parcelize
data class Ability(
    var name:String = ""
): Parcelable



