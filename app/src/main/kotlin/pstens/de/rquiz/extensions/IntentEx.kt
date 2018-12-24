package pstens.de.rquiz.extensions

import android.app.Activity
import android.content.Intent

fun Intent.start(act: Activity) {
    act.startActivity(this)
}