package ru.aleshin.news_feature_impl.presentaiton.ui.news.adapters

import androidx.recyclerview.widget.DiffUtil
import ru.aleshin.news_feature_impl.presentaiton.models.NewsUi

/**
 * @author Stanislav Aleshin on 15.10.2022.
 */
internal class NewsDiffUtil : DiffUtil.ItemCallback<NewsUi>() {

    override fun areItemsTheSame(oldItem: NewsUi, newItem: NewsUi): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: NewsUi, newItem: NewsUi): Boolean {
        return oldItem == newItem
    }
}