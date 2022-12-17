package ru.aleshin.news_details_feature_impl.presentation.ui.details.communications

import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.FlowCollector
import ru.aleshin.core.platform.communication.Communicator
import ru.aleshin.news_details_feature_impl.domain.entities.NewsDetailsEntity
import ru.aleshin.news_details_feature_impl.presentation.ui.details.common.DetailsUiState
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 17.12.2022.
 */
internal interface NewsDetailsCommunications : NewsDetailsCommunicationsCollect {

    fun showState(state: DetailsUiState)

    fun showDetailsNews(news: NewsDetailsEntity)

    suspend fun fetchDetailsNews(): NewsDetailsEntity

    suspend fun fetchState(): DetailsUiState

    class Base @Inject constructor(
        private val stateCommunicator: NewsDetailsStateCommunicator,
        private val newsCommunicator: NewsDetailsCommunicator
    ) : NewsDetailsCommunications {

        override fun showState(state: DetailsUiState) {
            stateCommunicator.update(state)
        }

        override fun showDetailsNews(news: NewsDetailsEntity) {
            newsCommunicator.update(news)
        }

        override fun collectState(
            lifecycleOwner: LifecycleOwner,
            collector: FlowCollector<DetailsUiState>
        ) = stateCommunicator.collect(lifecycleOwner, collector)

        override fun collectNewsDetails(
            lifecycleOwner: LifecycleOwner,
            collector: FlowCollector<NewsDetailsEntity>
        ) = newsCommunicator.collect(lifecycleOwner, collector)

        override suspend fun fetchState() = stateCommunicator.read()

        override suspend fun fetchDetailsNews() = newsCommunicator.read()
    }
}

internal interface NewsDetailsStateCommunicator : Communicator.Combined<DetailsUiState> {

    class Base @Inject constructor() : NewsDetailsStateCommunicator,
        Communicator.AbstractStateFlow<DetailsUiState>(DetailsUiState.Empty)
}

internal interface NewsDetailsCommunicator : Communicator.Combined<NewsDetailsEntity> {

    class Base @Inject constructor() : NewsDetailsCommunicator,
        Communicator.AbstractSharedFlow<NewsDetailsEntity>()
}