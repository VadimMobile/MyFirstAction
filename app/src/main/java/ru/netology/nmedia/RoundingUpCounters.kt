package ru.netology.nmedia

object Utils {
    fun formatCounter(counter: Long): String {
        val result = if (counter in 1000..9_999) {
            val lastDigit = (counter / 100) % 10
            "${counter / 1000}${if (lastDigit != 0.toLong()) ".$lastDigit" else ""}K"
        } else if (counter in 10_000..999_999) {
            "${counter / 1000}K"
        } else if (counter >= 1_000_000) {
            val lastDigit = (counter / 100000) % 10
            "${counter / 1_000_000}${if (lastDigit != 0.toLong()) ".$lastDigit" else ""}M"
        } else {
            "$counter"
        }
        return result
    }
}