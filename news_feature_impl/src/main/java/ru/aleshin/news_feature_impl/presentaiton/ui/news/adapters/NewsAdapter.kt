package ru.aleshin.news_feature_impl.presentaiton.ui.news.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import ru.aleshin.news_feature_impl.presentaiton.models.NewsUi
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 15.10.2022.
 */
internal class NewsAdapter @Inject constructor() : PagingDataAdapter<NewsUi, NewsViewHolder>(
    diffCallback = NewsDiffUtil()
) {

    private var listener: ((NewsUi?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position), requireClickListener())
    }

    fun setOnItemClickListener(listener: (NewsUi?) -> Unit) {
        this.listener = listener
    }

    private fun requireClickListener() = checkNotNull(listener) {
        "OnItemClickListener is not initializing"
    }
}
