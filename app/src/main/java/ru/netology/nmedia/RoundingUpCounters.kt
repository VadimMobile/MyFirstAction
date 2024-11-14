package ru.netology.nmedia

fun formatCounter (counter: Int): String {
    if (counter >= 1000) {counter / 1000}
    else if (counter >= 10_000) {counter / 1000}
        else if (counter >= 1_000_000) {counter / 1_000_000}
    counter.toString()
    return counter.toString() + "k"
}