package pstens.de.rquiz

import android.view.View

fun View.onClick(block: () -> Unit) {
    setOnClickListener { block() }
}