package ru.aleshin.core.platform.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */

abstract class BaseBottomSheetFragment<VB : ViewBinding, VM : ViewModel>(
    private val bindingInflater: (View) -> VB,
    @LayoutRes private val layoutRes: Int
) : BottomSheetDialogFragment() {

    protected val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, fetchViewModelFactory())[fetchViewModelClass()]
    }

    private var viewBinding: VB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        initDI()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = layoutInflater.inflate(layoutRes, container, false)
        viewBinding = bindingInflater.invoke(view)

        return requireViewBinding().root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData(savedInstanceState)
        initView(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    abstract fun fetchViewModelClass(): Class<VM>

    abstract fun fetchViewModelFactory(): ViewModelProvider.Factory

    open fun initDI() {}

    open fun initData(savedInstanceState: Bundle?) {}

    open fun initView(savedInstanceState: Bundle?) {}

    protected fun requireViewBinding() = checkNotNull(viewBinding) { "ViewBinding is nullable" }
}