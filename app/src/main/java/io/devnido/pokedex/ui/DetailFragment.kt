package io.devnido.pokedex.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.anychart.charts.Radar.instantiate
import com.anychart.core.axes.Radar.instantiate
import com.anychart.core.radar.series.Line
import com.anychart.data.Mapping
import com.anychart.data.Set
import com.anychart.enums.Align
import com.anychart.enums.MarkerType
import com.bumptech.glide.Glide
import io.devnido.pokedex.core.Result
import io.devnido.pokedex.core.Utils
import io.devnido.pokedex.core.hide
import io.devnido.pokedex.core.show
import io.devnido.pokedex.databinding.FragmentDetailBinding
import io.devnido.pokedex.di.components.DaggerAppComponent
import io.devnido.pokedex.domain.entities.Pokemon
import io.devnido.pokedex.ui.viewmodels.PokemonViewModel
import kotlinx.android.synthetic.main.pokemon_card.view.*
import javax.inject.Inject
import javax.inject.Provider


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val safeArg by navArgs<DetailFragmentArgs>()



    private lateinit var pokemon: Pokemon

    private lateinit var pokemonViewModel: PokemonViewModel

    @Inject
    lateinit var viewModelProvider: Provider<PokemonViewModel>

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pokemon = safeArg.pokemon

        pokemonViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                viewModelProvider.get() as T
        }).get(PokemonViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInitialUI()
        getPokemonDetail()

    }

    @SuppressLint("SetTextI18n")
    private fun setupInitialUI(){
        with(binding){

            Glide.with(requireContext()).load(pokemon.images.large).centerInside().into(imgPokemon)

            txtNamePokemon.text = pokemon.name
            txtIdPokemon.text = "# ${pokemon.id}"

        }
    }

    @SuppressLint("SetTextI18n", "LongLogTag")
    private fun setupDetailUI(){
        with(binding){

            containerDetail.show()

            Log.d("TAG_POKEMON_FRAGMENT", pokemon.toString())
            pokemon.types?.first?.let { type ->
                containerPokemonTypes.txtFirstType.text = type
                val color = Utils.getColorByPokemonType(context, type)
                color?.let {
                    containerPokemonTypes.txtFirstType.background.colorFilter = PorterDuffColorFilter(
                        color,
                        PorterDuff.Mode.SRC
                    )
                }
            }

            pokemon.types?.second?.let { type ->
                Log.d("TAG_POKEMON_FRAGMENT_SECOND_TYPE", type)
                containerPokemonTypes.txtSecondType.visibility = View.VISIBLE
                containerPokemonTypes.txtSecondType.text = type
                val color = Utils.getColorByPokemonType(context, type)
                color?.let {
                    containerPokemonTypes.txtSecondType.background.colorFilter = PorterDuffColorFilter(
                        color,
                        PorterDuff.Mode.SRC
                    )
                }
            }

            containerPokemonInfo.infoOrder.text = pokemon.order.toString()
            containerPokemonInfo.infoExpBase.text = pokemon.baseExperience.toString()

            pokemon.height?.let {
                containerPokemonInfo.infoHeight.text = "$it m"
            }

            pokemon.weight?.let {
                containerPokemonInfo.infoWeight.text = "$it kg"
            }

            pokemon.abilities?.let {
               containerPokemonInfo.infoAbilities.text = it.joinToString(
                   separator = ", ",
                   transform = { ability -> ability.name })
            }


            setupChart()
            setupSprites()
        }


    }

    private fun getPokemonDetail(){
        Log.d("TAG_POKEMON_FD", pokemon.name)
        pokemonViewModel.getPokemonDetail(pokemon.id).observe(
            viewLifecycleOwner,
            Observer { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressDetailInfo.show()
                    }
                    is Result.Success -> {
                        @Suppress("UNCHECKED_CAST")
                        pokemon = result.data as Pokemon

                        binding.progressDetailInfo.hide()
                        setupDetailUI()

                    }
                    is Result.Error -> {
                        val exception: Exception = result.exception
                        binding.progressDetailInfo.hide()
                        Toast.makeText(requireContext(), exception.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
    }

    private fun setupChart(){
        val anyChartView: AnyChartView = binding.containerPokemonChart.anyChartView

        val radar: Radar = AnyChart.radar()
        
        radar.yScale().minimum(0.0)
        radar.yScale().minimumGap(0.0)
        radar.yScale().ticks().interval(20.0)

        radar.xAxis().labels().padding(5.0, 5.0, 5.0, 5.0)

        radar.legend()
            .align(Align.CENTER)
            .enabled(true)

        val data: MutableList<DataEntry> = ArrayList()
        pokemon.stats?.map {
            data.add(ValueDataEntry(it.name, it.base_value))
        }


        val set: Set = Set.instantiate()
        set.data(data)
        val baseStats: Mapping = set.mapAs("{ x: 'x', value: 'value' }")

        val baseStatsLine: Line = radar.line(baseStats)
        baseStatsLine.name("Stats")
        baseStatsLine.markers()
            .enabled(true)
            .type(MarkerType.CIRCLE)
            .size(3.0)


        radar.tooltip().format("Value: {%Value}")

        anyChartView.setChart(radar)
    }

    private fun setupSprites(){
        with(binding.containerPokemonSprites){
            Glide.with(requireContext()).load(pokemon.images.defaultFront).centerInside().into(imgSpriteDefaultFront)
            Glide.with(requireContext()).load(pokemon.images.defaultBack).centerInside().into(imgSpriteDefaultBack)
            Glide.with(requireContext()).load(pokemon.images.shinyFront).centerInside().into(imgSpriteShinyFront)
            Glide.with(requireContext()).load(pokemon.images.shinyBack).centerInside().into(imgSpriteShinyBack)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}