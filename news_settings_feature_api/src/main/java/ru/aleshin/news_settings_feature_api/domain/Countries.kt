package ru.aleshin.news_settings_feature_api.domain

import androidx.annotation.StringRes
import ru.aleshin.core_ui.R

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
enum class Countries(val data: String, @StringRes val title: Int) {
    AUSTRALIA("au", R.string.australia_country),
    BRAZIL("br", R.string.brazil_country),
    CANADA("ca", R.string.canada_country),
    SWITZERLAND("ch", R.string.switzerland_country),
    CHINA("cn", R.string.chine_country),
    GERMANY("de", R.string.germany_country),
    EGYPT("eg", R.string.egypt_country),
    SPAIN("es", R.string.spain_country),
    FRANCE("fr", R.string.france_country),
    UNITED_KINGDOM("gb", R.string.united_kingdom_country),
    GREECE("gr", R.string.greece_country),
    HONG_KONG("hk", R.string.hong_kong_country),
    IRELAND("ie", R.string.ireland_country),
    ISRAEL("il", R.string.israel_country),
    INDIA("in", R.string.india_country),
    ITALY("it", R.string.italy_country),
    JAPAN("jp", R.string.japan_country),
    NETHERLANDS("nl", R.string.netherlands_country),
    NORWAY("no", R.string.norway_country),
    PERU("pe", R.string.peru_country),
    PHILIPPINES("ph", R.string.philippines_country),
    PAKISTAN("pk", R.string.pakistan_country),
    PORTUGAL("pt", R.string.portugal_country),
    ROMANIA("ro", R.string.romania_country),
    RUSSIAN_FEDERATION("ru", R.string.russian_federation_country),
    SWEDEN("se", R.string.sweden_country),
    SINGAPORE("sg", R.string.singapore_country),
    TAIWAN("tw", R.string.taiwan_country),
    UKRAINE("ua", R.string.ukraine_country),
    USA("usa", R.string.united_states_country);
}

/**
 * Method for converting [String] to [Countries] ENUM
 *
 * @param String the which contains country
 * @return [Countries]
 */
fun String.convertToCountry(): Countries {
    val region = Countries.values().find { it.data == this }
    return region ?: throw IllegalArgumentException("Region: $this is not found in enum list!")
}