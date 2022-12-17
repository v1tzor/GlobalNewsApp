package ru.aleshin.news_details_feature_impl.presentation.ui.details

import android.os.Bundle
import ru.aleshin.core.extensions.loadImage
import ru.aleshin.core.platform.fragments.BaseFragmentWithViewModel
import ru.aleshin.news_details_feature_impl.R
import ru.aleshin.news_details_feature_impl.databinding.NewsDetailsFragmentBinding
import ru.aleshin.news_details_feature_impl.di.holder.NewsDetailsComponentHolder
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 15.10.2022.
 */
internal class NewsDetailsFragment :
    BaseFragmentWithViewModel<NewsDetailsFragmentBinding, NewsDetailsViewModel>(
        bindingInflater = NewsDetailsFragmentBinding::inflate
    ) {

    @Inject
    lateinit var viewModelFactory: NewsDetailsViewModel.Factory

    override fun initDI() {
        super.initDI()
        NewsDetailsComponentHolder.fetchComponent().inject(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        viewModel.collectState(viewLifecycleOwner) { state -> state.apply(requireViewBinding()) }

        viewModel.collectNewsDetails(viewLifecycleOwner) { settings ->
            with(requireViewBinding()) {
                newsImage.loadImage(settings.imageUrl, R.drawable.missing_photo)
                sourceTitle.text = settings.source
                contentTitle.text = settings.content
                timeTitle.text = settings.publishedAt
            }
        }

        viewModel.init(savedInstanceState == null)
    }

    override fun initView(savedInstanceState: Bundle?) = with(requireViewBinding()) {
        super.initView(savedInstanceState)

        toolbar.setNavigationOnClickListener { viewModel.pressBackButton() }

        toolbar.setOnMenuItemClickListener { viewModel.pressShareButton(); true }

        sourceButton.setOnClickListener { viewModel.pressSourceButton() }
    }

    override fun fetchViewModelClass() = NewsDetailsViewModel::class.java

    override fun fetchViewModelFactory() = viewModelFactory
}
