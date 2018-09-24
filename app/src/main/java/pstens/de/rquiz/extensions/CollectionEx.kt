package pstens.de.rquiz.extensions

fun <T> Collection<T>.pickRandom(amount: Int = 1) = shuffled().take(amount)
