package es.headbe.texnes.util.iteratorUtil

fun <T, U> Iterable<T>.zipAll(other: U): List<Pair<T, U>> {
    return this.map { Pair(it, other) }
}

fun <T, U, V> Pair<T, U>.with(other: V): Triple<T, U, V> {
    val (a, b) = this
    return Triple(a, b, other)
}

