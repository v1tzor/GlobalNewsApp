package ru.aleshin.news_favorites_feature_impl.presentation.ui

import android.os.Bundle
import ru.aleshin.core.platform.fragments.BaseFragmentWithViewModel
import ru.aleshin.news_favorites_feature_impl.databinding.FavoritesFragmnetBinding
import ru.aleshin.news_favorites_feature_impl.di.holder.NewsFavoritesComponentHolder
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 28.10.2022.
 */

internal class FavoritesFragment :
    BaseFragmentWithViewModel<FavoritesFragmnetBinding, FavoritesViewModel>(
        FavoritesFragmnetBinding::inflate
    ) {

    @Inject
    lateinit var factory: FavoritesViewModel.Factory

    override fun fetchViewModelClass() = FavoritesViewModel::class.java

    override fun fetchViewModelFactory() = factory

    override fun initDI() {
        super.initDI()
        NewsFavoritesComponentHolder.fetchComponent().inject(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
    }
}