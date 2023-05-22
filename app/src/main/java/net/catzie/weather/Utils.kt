package net.catzie.weather

import kotlin.math.abs

object Utils {

    object Randoms {
        fun getRandomId(): Int {
            return abs((0..9999).random())
        }
    }
}