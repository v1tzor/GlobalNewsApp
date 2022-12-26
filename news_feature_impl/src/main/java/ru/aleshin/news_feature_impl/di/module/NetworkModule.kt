package ru.aleshin.news_feature_impl.di.module

import android.content.Context
import androidx.paging.PagingConfig
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.aleshin.core.di.FeatureScope
import ru.aleshin.news_feature_impl.BuildConfig
import ru.aleshin.news_feature_impl.data.data_sources.network.NewsPagingDataSource
import ru.aleshin.news_feature_impl.data.data_sources.network.service.NewsInterceptor
import ru.aleshin.news_feature_impl.data.data_sources.network.service.NewsService
import ru.aleshin.news_feature_impl.di.annotation.LoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * @author Stanislav Aleshin on 18.10.2022.
 */
@Module
internal class NetworkModule {

    @Provides
    @FeatureScope
    fun provideNewsPagingConfig(): PagingConfig {
        return PagingConfig(
            pageSize = NewsPagingDataSource.PAGE_SIZE,
            enablePlaceholders = true
        )
    }

    @Provides
    @FeatureScope
    fun provideNewsInterceptor(context: Context): Interceptor {
        return if (BuildConfig.DEBUG) NewsInterceptor.Mock(context) else NewsInterceptor.Base()
    }

    @Provides
    @FeatureScope
    @LoggingInterceptor
    fun provideHttpLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) Level.BODY
            else Level.NONE
        )
    }

    @Provides
    @FeatureScope
    fun provideOkHttpClient(
        newsInterceptor: Interceptor,
        @LoggingInterceptor loggingInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(newsInterceptor)
            .addNetworkInterceptor(loggingInterceptor)
            .callTimeout(NEWS_CALL_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @FeatureScope
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.GNEWS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @FeatureScope
    fun provideNewsService(retrofit: Retrofit): NewsService {
        return retrofit.create(NewsService::class.java)
    }
}

const val NEWS_CALL_TIMEOUT = 8L