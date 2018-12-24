package pstens.de.rquiz.extensions

import android.util.Log

fun Any.d(message: String) {
    Log.d(javaClass.simpleName, message)
}

fun Any.i(message: String) {
    Log.i(javaClass.simpleName, message)
}

fun Any.e(message: String) {
    Log.e(javaClass.simpleName, message)
}
