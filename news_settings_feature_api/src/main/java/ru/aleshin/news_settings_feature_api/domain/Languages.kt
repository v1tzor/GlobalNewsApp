package ru.aleshin.news_settings_feature_api.domain

import androidx.annotation.StringRes
import ru.aleshin.core_ui.R

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
enum class Languages(val data: String, @StringRes val title: Int) {
    ARABIC("ar", R.string.arabic_language),
    GERMAN("de", R.string.german_language),
    GREEK("el", R.string.greek_language),
    ENGLISH("en", R.string.english_language),
    SPANISH("es", R.string.spanish_language),
    FRENCH("fr", R.string.french_language),
    ITALIAN("it", R.string.italian_language),
    JAPANESE("ja", R.string.japanese_language),
    NORWEGIAN("no", R.string.norwegian_language),
    PORTUGUESE("pt", R.string.portuguese_language),
    ROMANIAN("ro", R.string.romanian_language),
    RUSSIAN("ru", R.string.russian_language),
    SWEDISH("sv", R.string.swedish_language),
    UKRAINIAN("uk", R.string.ukrainian_language),
    CHINESE("zh", R.string.chinese_language);
}

/**
 * Method for converting [String] to [Languages] ENUM
 *
 * @param String the which contains shorten language
 * @return [Languages]
 */
fun String.convertToLanguage(): Languages {
    val languages = Languages.values().find { it.data == this }
    return languages ?: throw IllegalArgumentException("Language: $this is not found in enum list!")
}