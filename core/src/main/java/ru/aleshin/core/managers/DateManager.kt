package ru.aleshin.core.managers

import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Stanislav Aleshin on 25.11.2022.
 */
interface DateManager {

    fun parseIsoInstant(date: String): String

    class Base : DateManager {

        override fun parseIsoInstant(date: String): String {
            val currentDate = SimpleDateFormat(ISO_INSTANT, Locale.getDefault()).parse(date) ?: ""
            val correctDateFormat = SimpleDateFormat.getDateTimeInstance() // ("EEE, d MMM yyyy HH:mm:ss", Locale.getDefault())

            return correctDateFormat.format(currentDate)
        }

        companion object {
            const val ISO_INSTANT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        }
    }
}