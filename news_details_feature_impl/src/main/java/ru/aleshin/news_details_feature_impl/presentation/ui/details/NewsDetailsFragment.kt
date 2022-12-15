package ru.aleshin.news_details_feature_impl.presentation.ui.details

import android.os.Bundle
import com.google.android.material.transition.MaterialFadeThrough
import ru.aleshin.core.platform.fragments.BaseFragmentWithViewModel
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
        reenterTransition = MaterialFadeThrough()
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        viewModel.collectState(viewLifecycleOwner) { state -> state.apply(requireViewBinding()) }

        viewModel.init(savedInstanceState == null)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        requireViewBinding().backButton.setOnClickListener { viewModel.pressBackButton() }
    }

    override fun fetchViewModelClass() = NewsDetailsViewModel::class.java

    override fun fetchViewModelFactory() = viewModelFactory
}
