package pstens.de.rquiz.extensions

import android.widget.ImageView
import com.squareup.picasso.Picasso
import pstens.de.rquiz.R

fun ImageView.loadUrl(url: String) {
    if (url.isBlank()) {
        Picasso.get()
            .load(R.drawable.ic_launcher_foreground)
            .fit()
            .centerCrop()
            .into(this)
    } else {
        Picasso.get()
            .load(url)
            .fit()
            .centerCrop()
            .into(this)
    }
}