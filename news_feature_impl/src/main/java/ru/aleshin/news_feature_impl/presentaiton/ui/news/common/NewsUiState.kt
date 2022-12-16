package ru.aleshin.news_feature_impl.presentaiton.ui.news.common

import androidx.core.view.isVisible
import ru.aleshin.core.extensions.checkByTag
import ru.aleshin.news_feature_impl.R
import ru.aleshin.news_feature_impl.databinding.NewsFragmentBinding
import ru.aleshin.news_feature_impl.domain.entites.NewsFailures
import ru.aleshin.news_settings_feature_api.domain.Categories
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
internal sealed class NewsUiState {

    abstract fun apply(viewBinding: NewsFragmentBinding)

    abstract class Abstract(private val isLoading: Boolean) : NewsUiState() {
        override fun apply(viewBinding: NewsFragmentBinding) = with(viewBinding) {
            if (!isLoading) recyclerSwipeRefresh.isRefreshing = false
            recyclerSwipeRefresh.isVisible = !isLoading
            shimmerFrameLayout.isVisible = isLoading
        }
    }

    object News : Abstract(false) {
        override fun apply(viewBinding: NewsFragmentBinding) = with(viewBinding) {
            super.apply(viewBinding)
            errorTitle.isVisible = false
        }
    }

    object Loading : Abstract(true) {
        override fun apply(viewBinding: NewsFragmentBinding) = with(viewBinding) {
            super.apply(viewBinding)
            shimmerFrameLayout.startShimmer()
        }
    }

    class Error(private val failure: NewsFailures) : Abstract(false) {

        private val failuresTitle = mapOf(
            NewsFailures.DataBaseException::class.java to R.string.data_base_exception_title,
            NewsFailures.NetworkException::class.java to R.string.network_exception_title,
            NewsFailures.ServiceException::class.java to R.string.service_unavailable_exception_title,
            NewsFailures.OtherException::class.java to R.string.other_exception_title
        )

        override fun apply(viewBinding: NewsFragmentBinding) = with(viewBinding) {
            super.apply(viewBinding)
            errorTitle.isVisible = true
            errorTitle.setText(checkNotNull(failuresTitle[failure.javaClass]))
        }
    }

    class Init(private val defualtCategory: Categories) : Abstract(false) {

        override fun apply(viewBinding: NewsFragmentBinding) = with(viewBinding) {
            super.apply(viewBinding)
            categoryChipGroup.checkByTag(defualtCategory.data)
        }
    }

    object Empty : Abstract(false)
}