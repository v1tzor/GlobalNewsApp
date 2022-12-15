package ru.aleshin.news_feature_impl.data.models

import com.google.gson.annotations.SerializedName

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
data class Articles(
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("content") val content: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("publishedAt") val publishedAt: String? = null,
    @SerializedName("source") val source: Source? = Source()
)