package ru.aleshin.news_settings_feature_api.domain

import androidx.annotation.StringRes
import ru.aleshin.core_ui.R

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
enum class Categories(val data: String, @StringRes val title: Int) {
    BREAKING_NEWS("breaking-news", R.string.category_breaking_news),
    WORLD("world", R.string.category_world_news),
    NATION("nation", R.string.category_nation_news),
    BUSINESS("business", R.string.category_business_news),
    TECHNOLOGY("technology", R.string.category_technology_news),
    ENTERTAINMENT("entertainment", R.string.category_entertainment_news),
    SPORTS("sports", R.string.category_sports_news),
    SCIENCE("science", R.string.category_science_news),
    HEALTH("health", R.string.category_health_news);
}

/**
 * Method for converting [String] to Categories ENUM
 *
 * @param String the which contains category
 * @return [Categories]
 */
fun String.convertToCategory(): Categories {
    val category = Categories.values().find { it.data == this }
    return category ?: throw IllegalArgumentException("Category: $this is not found in enum list!")
}