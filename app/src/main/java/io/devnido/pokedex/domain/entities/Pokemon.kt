package io.devnido.pokedex.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
    var number: Int,
    var name: String,
    var images: Images,
    var types: Types? = null
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
    var first:String,
    var second:String = ""
): Parcelable



