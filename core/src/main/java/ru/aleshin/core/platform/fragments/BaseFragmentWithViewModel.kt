package ru.aleshin.core.platform.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
abstract class BaseFragmentWithViewModel<VB : ViewBinding, VM : ViewModel>(
    bindingInflater: FragmentBindingInflater<VB>
) : BaseFragment<VB>(bindingInflater) {

    protected val viewModel: VM by lazy {
        ViewModelProvider(this, fetchViewModelFactory())[fetchViewModelClass()]
    }

    abstract fun fetchViewModelClass(): Class<VM>

    abstract fun fetchViewModelFactory(): ViewModelProvider.Factory
}