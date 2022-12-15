package ru.aleshin.news_feature_impl.data.models

import com.google.gson.annotations.SerializedName

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
data class Source(
    @SerializedName("name") val name: String? = null,
    @SerializedName("url") val url: String? = null
)