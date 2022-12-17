package ru.aleshin.news_details_feature_impl.presentation.ui.details.communications

import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.FlowCollector
import ru.aleshin.news_details_feature_impl.domain.entities.NewsDetailsEntity
import ru.aleshin.news_details_feature_impl.presentation.ui.details.common.DetailsUiState

/**
 * @author Stanislav Aleshin on 17.12.2022.
 */
internal interface NewsDetailsCommunicationsCollect {

    fun collectState(
        lifecycleOwner: LifecycleOwner,
        collector: FlowCollector<DetailsUiState>
    )

    fun collectNewsDetails(
        lifecycleOwner: LifecycleOwner,
        collector: FlowCollector<NewsDetailsEntity>
    )
}
