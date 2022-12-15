package ru.aleshin.news_feature_impl.data.models

import com.google.gson.annotations.SerializedName

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
internal data class NewsResponse(
    @SerializedName("totalArticles") val totalArticles: Int? = null,
    @SerializedName("articles") val articles: List<Articles> = listOf()
)
