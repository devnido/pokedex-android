package io.devnido.pokedex.di.components

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import io.devnido.pokedex.di.modules.BinderModule
import io.devnido.pokedex.di.modules.DbModule
import io.devnido.pokedex.di.modules.NetworkModule
import io.devnido.pokedex.di.modules.RepositoryModule
import io.devnido.pokedex.ui.DetailFragment
import io.devnido.pokedex.ui.ListFragment
import io.devnido.pokedex.ui.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [DbModule::class,NetworkModule::class,RepositoryModule::class,BinderModule::class])
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(listFragment: ListFragment)
    fun inject(detailFragment: DetailFragment)
}