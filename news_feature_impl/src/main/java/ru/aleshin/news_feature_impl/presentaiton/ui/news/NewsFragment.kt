package ru.aleshin.news_feature_impl.presentaiton.ui.news

import android.os.Bundle
import ru.aleshin.core.extensions.addChip
import ru.aleshin.core.extensions.checkedChipButtonTag
import ru.aleshin.core.platform.fragments.BaseFragmentWithViewModel
import ru.aleshin.news_feature_impl.databinding.NewsFragmentBinding
import ru.aleshin.news_feature_impl.di.holder.NewsComponentHolder
import ru.aleshin.news_feature_impl.presentaiton.ui.news.adapters.NewsAdapter
import ru.aleshin.news_feature_impl.presentaiton.ui.news.adapters.NewsLoadStateAdapter
import ru.aleshin.news_settings_feature_api.domain.Categories
import ru.aleshin.news_settings_feature_api.domain.convertToCategory
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
internal class NewsFragment : BaseFragmentWithViewModel<NewsFragmentBinding, NewsViewModel>(
    NewsFragmentBinding::inflate
) {

    @Inject
    lateinit var factory: NewsViewModel.Factory

    @Inject
    lateinit var newsAdapter: NewsAdapter

    @Inject
    lateinit var loadStateAdapter: NewsLoadStateAdapter

    override fun initDI() = NewsComponentHolder.fetchComponent().inject(this)

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        viewModel.init(savedInstanceState == null)

        viewModel.collectState(viewLifecycleOwner) { state -> state.apply(requireViewBinding()) }

        viewModel.collectNews(viewLifecycleOwner) { pagingData ->
            newsAdapter.submitData(lifecycle, pagingData)
        }

        newsAdapter.withLoadStateHeaderAndFooter(
            header = loadStateAdapter,
            footer = loadStateAdapter
        )

        newsAdapter.setOnItemClickListener { news ->
            if (news != null) viewModel.pressNewsItem(news)
        }

        newsAdapter.addLoadStateListener { state ->
            viewModel.changedNewsLoadState(state.refresh)
        }

        loadStateAdapter.setOnRetryButtonClickListener {
            newsAdapter.retry()
        }
    }

    override fun initView(savedInstanceState: Bundle?) = with(requireViewBinding()) {
        super.initView(savedInstanceState)

        newsRecycler.adapter = newsAdapter

        recyclerSwipeRefresh.setOnRefreshListener { newsAdapter.refresh() }

        categoryChipGroup.setOnCheckedStateChangeListener { group, _ ->
            viewModel.changedCategory(group.checkedChipButtonTag().convertToCategory())
        }
    }

    override fun onDestroyView() {
        requireViewBinding().newsRecycler.adapter = null
        super.onDestroyView()
    }

    override fun fetchViewModelClass() = NewsViewModel::class.java

    override fun fetchViewModelFactory() = factory
}