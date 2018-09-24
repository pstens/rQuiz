package pstens.de.rquiz

fun <T> Collection<T>.pickRandom(amount: Int = 1) = shuffled().take(amount)
