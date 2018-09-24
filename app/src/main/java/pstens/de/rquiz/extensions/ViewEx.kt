package pstens.de.rquiz.extensions

import android.view.View

fun View.onClick(block: () -> Unit) {
    setOnClickListener { block() }
}