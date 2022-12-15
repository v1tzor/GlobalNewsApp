package ru.aleshin.news_details_feature_impl.presentation.ui.details.communications

import ru.aleshin.core.platform.communication.Communicator
import ru.aleshin.news_details_feature_impl.presentation.ui.details.common.DetailsUiState
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 29.10.2022.
 */
internal interface NewsDetailsStateCommunicator : Communicator.Combined<DetailsUiState> {

    class Base @Inject constructor() : NewsDetailsStateCommunicator,
        Communicator.AbstractStateFlow<DetailsUiState>(DetailsUiState.Empty)
}