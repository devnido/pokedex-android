package io.devnido.pokedex.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Radar
import com.anychart.core.radar.series.Line
import com.anychart.data.Mapping
import com.anychart.data.Set
import com.anychart.enums.Align
import com.anychart.enums.MarkerType
import com.bumptech.glide.Glide
import io.devnido.pokedex.core.Utils
import io.devnido.pokedex.core.hide
import io.devnido.pokedex.core.show
import io.devnido.pokedex.databinding.FragmentDetailBinding
import io.devnido.pokedex.domain.entities.Pokemon
import io.devnido.pokedex.ui.MainActivity
import io.devnido.pokedex.ui.viewmodels.PokemonViewModel
import javax.inject.Inject
import javax.inject.Provider


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val safeArg by navArgs<DetailFragmentArgs>()

    private lateinit var pokemonViewModel: PokemonViewModel

    @Inject
    lateinit var viewModelProvider: Provider<PokemonViewModel>

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pokemonViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                viewModelProvider.get() as T
        }).get(PokemonViewModel::class.java)


        pokemonViewModel.setPokemon(safeArg.pokemon)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        _binding?.lifecycleOwner = this
        _binding?.viewmodel = pokemonViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getPokemonDetail(safeArg.pokemon.id)

    }


    private fun getPokemonDetail(id: Int) {
        Log.d("TAG_POKEMON_DETAIL", id.toString())
        pokemonViewModel.getPokemonDetail(id)
    }


//    @SuppressLint("SetTextI18n")
//    private fun setupDetailUI(pokemon: Pokemon) {
//        with(binding) {
//
//            Log.d("TAG_POKEMON", "Detail")
//            containerDetail.show()


//            containerPokemonInfo.infoOrder.text = pokemon.order.toString()
//            containerPokemonInfo.infoExpBase.text = pokemon.baseExperience.toString()
//
//            pokemon.height?.let {
//                containerPokemonInfo.infoHeight.text = "$it m"
//            }
//
//            pokemon.weight?.let {
//                containerPokemonInfo.infoWeight.text = "$it kg"
//            }
//
//            pokemon.abilities?.let {
//                containerPokemonInfo.infoAbilities.text = it.joinToString(
//                    separator = ", ",
//                    transform = { ability -> ability.name })
//            }
//
//
//            setupChart(pokemon)
//        }


  //  }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}