package es.headbe.texnes.util

data class OpenFloatRange(val from: Float, val to: Float)
infix fun Float.until(to: Float) = OpenFloatRange(this, to)
operator fun OpenFloatRange.contains(f: Float) = from < f && f < to

