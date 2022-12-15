package ru.aleshin.news_details_feature_impl.presentation.ui.details.common

import androidx.core.view.isVisible
import ru.aleshin.core.extensions.loadImage
import ru.aleshin.core.extensions.openUrl
import ru.aleshin.news_details_feature_impl.R
import ru.aleshin.news_details_feature_impl.databinding.NewsDetailsFragmentBinding
import ru.aleshin.news_details_feature_impl.domain.entities.NewsDetailsEntity

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

    class News(private val data: NewsDetailsEntity) : Abstract(false) {
        override fun apply(
            viewBinding: NewsDetailsFragmentBinding
        ) = with(viewBinding) {
            super.apply(viewBinding)
            newsImage.loadImage(data.imageUrl, R.drawable.missing_photo)
            contentTitle.text = data.content
            timeTitle.text = data.publishedAt
            sourceTitle.text = data.source
            sourceButton.setOnClickListener { root.context.openUrl(data.sourceUrl) }
        }
    }

    object Error : Abstract(true) {
        override fun apply(viewBinding: NewsDetailsFragmentBinding) {
            super.apply(viewBinding)
            viewBinding.errorTitle.setText(R.string.other_exception_title)
        }
    }

    object Empty : Abstract(false)
}
