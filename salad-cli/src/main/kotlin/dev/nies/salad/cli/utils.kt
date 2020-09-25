package dev.nies.salad.cli

fun String.endingWith(suffix: String) = if (this.endsWith(suffix)) this else this.plus(suffix)
