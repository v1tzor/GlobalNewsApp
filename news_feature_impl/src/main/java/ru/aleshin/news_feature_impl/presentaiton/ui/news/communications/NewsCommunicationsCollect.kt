package ru.aleshin.news_feature_impl.presentaiton.ui.news.communications

import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingData
import kotlinx.coroutines.flow.FlowCollector
import ru.aleshin.news_feature_impl.presentaiton.models.NewsUi
import ru.aleshin.news_feature_impl.presentaiton.ui.news.common.NewsUiState

/**
 * @author Stanislav Aleshin on 07.10.2022.
 */
internal interface NewsCommunicationsCollect {

    fun collectState(
        lifecycleOwner: LifecycleOwner,
        collector: FlowCollector<NewsUiState>
    )

    fun collectNews(
        lifecycleOwner: LifecycleOwner,
        collector: FlowCollector<PagingData<NewsUi>>
    )
}