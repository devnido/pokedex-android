package io.devnido.pokedex.core

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:setImageUrl")
 fun bindImageUrl(view: ImageView, url: String?) {
    if (url != null && url.isNotBlank()) {
        Glide.with(view.context).load(url).centerInside().into(view)
    }
}



