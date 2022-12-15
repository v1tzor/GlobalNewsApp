package ru.aleshin.news_feature_impl.data.data_sources.network.service

import android.content.Context
import android.content.res.AssetManager
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import ru.aleshin.news_feature_impl.BuildConfig

/**
 * @author Stanislav Aleshin on 18.10.2022.
 */

internal interface NewsInterceptor : Interceptor {

    class Base : NewsInterceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val newUrl = chain.request().url.newBuilder()
                .addQueryParameter(API_KEY_QUERY_NAME, BuildConfig.GNEWS_API_KEY)
                .build()

            val newRequest = chain.request().newBuilder()
                .url(newUrl)
                .build()

            return chain.proceed(newRequest)
        }

        companion object {
            private const val API_KEY_QUERY_NAME = "token"
        }
    }

    class Mock(private val context: Context) : NewsInterceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val uri = chain.request().url.toUri().toString()
            val jsonString = when {
                uri.contains("breaking-news") -> context.assets.readAssetsFile("mock_api_breaking_news.json")
                else -> context.assets.readAssetsFile("mock_api_breaking_news.json")
            }
            return chain.proceed(chain.request()).newBuilder()
                .code(SUCCESS_CODE)
                .protocol(Protocol.HTTP_3)
                .message(jsonString)
                .body(jsonString.toResponseBody("application/json".toMediaTypeOrNull()))
                .build()
        }
    }

    companion object {
        private const val SUCCESS_CODE = 200
    }
}

fun AssetManager.readAssetsFile(fileName: String): String =
    open(fileName).bufferedReader().use { it.readText() }
