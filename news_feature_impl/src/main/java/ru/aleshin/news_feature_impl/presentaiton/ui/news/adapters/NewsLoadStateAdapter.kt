package ru.aleshin.news_feature_impl.presentaiton.ui.news.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import ru.aleshin.core.platform.adapter.BaseViewHolder
import ru.aleshin.news_feature_impl.R
import ru.aleshin.news_feature_impl.databinding.ErrorNewsItemBinding
import ru.aleshin.news_feature_impl.databinding.LoadingNewsItemBinding
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 16.10.2022.
 */
internal class NewsLoadStateAdapter @Inject constructor() :
    LoadStateAdapter<BaseViewHolder<LoadState>>() {

    private var listener: (() -> Unit)? = null

    override fun getStateViewType(loadState: LoadState) = when (loadState) {
        is LoadState.Loading -> R.layout.loading_news_item
        is LoadState.Error -> R.layout.error_news_item
        is LoadState.NotLoading -> error("NewsLoadStateAdapter is not supported NotLoading state!")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = when (loadState) {
        is LoadState.Loading -> LoadingViewHolder.create(parent)
        is LoadState.Error -> ErrorViewHolder.create(parent, requireRetryListener())
        is LoadState.NotLoading -> error("NewsLoadStateAdapter is not supported NotLoading state!")
    }

    override fun onBindViewHolder(holder: BaseViewHolder<LoadState>, loadState: LoadState) {
        holder.bind(loadState)
    }

    fun setOnRetryButtonClickListener(listener: () -> Unit) {
        this.listener = listener
    }

    private fun requireRetryListener() = checkNotNull(listener) {
        "OnRetryButtonClickListener is not initializing"
    }

    class ErrorViewHolder(
        private val viewBinding: ErrorNewsItemBinding,
        private val retryListener: () -> Unit
    ) : BaseViewHolder<LoadState>(viewBinding) {

        override fun bind(data: LoadState) = with(viewBinding) {
            tryAgainButton.setOnClickListener { retryListener.invoke() }
        }

        companion object {
            fun create(parent: ViewGroup, retryListener: () -> Unit): ErrorViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val viewBinding = ErrorNewsItemBinding.inflate(layoutInflater, parent, false)

                return ErrorViewHolder(viewBinding, retryListener)
            }
        }
    }

    class LoadingViewHolder(
        viewBinding: LoadingNewsItemBinding
    ) : BaseViewHolder<LoadState>(viewBinding) {

        companion object {
            fun create(parent: ViewGroup): LoadingViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val viewBinding = LoadingNewsItemBinding.inflate(layoutInflater, parent, false)

                return LoadingViewHolder(viewBinding)
            }
        }
    }
}