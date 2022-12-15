package ru.aleshin.core.platform.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * @author Stanislav Aleshin on 08.10.2022.
 */
abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: FragmentBindingInflater<VB>
) : Fragment() {

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
        viewBinding = bindingInflater.invoke(layoutInflater, checkNotNull(container), false)
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

    open fun initDI() {}

    open fun initData(savedInstanceState: Bundle?) {}

    open fun initView(savedInstanceState: Bundle?) {}

    protected fun requireViewBinding() = checkNotNull(viewBinding) { "ViewBinding is nullable" }
}

typealias FragmentBindingInflater<VB> = (LayoutInflater, ViewGroup, Boolean) -> VB