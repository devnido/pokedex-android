package io.devnido.pokedex.ui.detail

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.Log
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
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
import io.devnido.pokedex.core.Utils
import io.devnido.pokedex.domain.entities.Ability
import io.devnido.pokedex.domain.entities.Pokemon

@BindingAdapter("app:setColorByType")
fun setColorByType(view: TextView, type: String?){

    if (type != null && type.isNotBlank()) {
        val color = Utils.getColorByPokemonType(view.context, type)
        color?.let {
            view.background.colorFilter =
                PorterDuffColorFilter( color, PorterDuff.Mode.SRC )
        }
    }
}

@BindingAdapter("app:setAbilitiesText")
fun setAbilitiesText(view: TextView, abilities: List<Ability>?){
    abilities?.let {
        view.text = it.joinToString(
                    separator = ", ",
                    transform = { ability -> ability.name })
            }
}

@BindingAdapter("app:setFullWidth")
fun setFullWidth(view: TextView, hasSecondType: Boolean){

    if (!hasSecondType) {
        val params =
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2f)

        view.layoutParams = params
    }else{
        val params =
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)

        params.marginEnd = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, view.context.resources.displayMetrics).toInt()

        view.layoutParams = params
    }
}

@BindingAdapter("app:setChartData")
fun setChartData(anyChartView: AnyChartView, pokemon: Pokemon){

    pokemon.stats?.let {
        Log.d("TAG_POKEMON_STATS",pokemon.stats.toString())

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


        radar.tooltip().format("{%Value}")

        anyChartView.setChart(radar)
    }

}


