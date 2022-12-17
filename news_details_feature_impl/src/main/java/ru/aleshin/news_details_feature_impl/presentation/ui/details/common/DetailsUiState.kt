package ru.aleshin.news_details_feature_impl.presentation.ui.details.common

import androidx.core.view.isVisible
import ru.aleshin.news_details_feature_impl.R
import ru.aleshin.news_details_feature_impl.databinding.NewsDetailsFragmentBinding

/**
 * @author Stanislav Aleshin on 29.10.2022.
 */
internal sealed class DetailsUiState {

    abstract fun apply(viewBinding: NewsDetailsFragmentBinding)

    abstract class Abstract(private val isError: Boolean) : DetailsUiState() {
        override fun apply(
            viewBinding: NewsDetailsFragmentBinding
        ) = with(viewBinding) {
            errorTitle.isVisible = isError
            errorLayout.isVisible = isError
        }
    }

    object News : Abstract(false)

    object Error : Abstract(true) {
        override fun apply(viewBinding: NewsDetailsFragmentBinding) {
            super.apply(viewBinding)
            viewBinding.errorTitle.setText(R.string.other_exception_title)
        }
    }

    object Empty : Abstract(false)
}
