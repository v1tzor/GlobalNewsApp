package ru.aleshin.core.platform.dialogs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

/**
 * @author Stanislav Aleshin on 26.10.2022.
 */
abstract class BaseDialogFragmentWithViewModel<VB : ViewBinding, VM : ViewModel>(
    bindingInflater: DialogBindingInflater<VB>
) : BaseDialogFragment<VB>(bindingInflater) {

    protected val viewModel: VM by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, fetchViewModelFactory())[fetchViewModelClass()]
    }

    abstract fun fetchViewModelClass(): Class<VM>

    abstract fun fetchViewModelFactory(): ViewModelProvider.Factory
}