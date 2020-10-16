package io.devnido.pokedex

import android.app.Application
import io.devnido.pokedex.di.components.AppComponent
import io.devnido.pokedex.di.components.DaggerAppComponent

class BaseApplication  : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}