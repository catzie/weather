package net.catzie.weather

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

object Utils {

    object Randoms {
        fun getRandomId(): Int {
            return abs((0..9999).random())
        }
    }

    object Format {

        fun formatTime(timeInMillis: Long): String {

            val dateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
            val dateTime = Date(timeInMillis)

            return dateFormat.format(dateTime)
        }
    }
}