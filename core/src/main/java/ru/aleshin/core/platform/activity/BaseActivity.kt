package ru.aleshin.core.platform.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

/**
 * @author Stanislav Aleshin on 18.10.2022.
 */
abstract class BaseActivity<VB : ViewBinding, VM : ViewModel>(
    private val bindingInflater: ActivityBindingInflater<VB>
) : AppCompatActivity() {

    protected val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }

    protected val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, fetchViewModelFactory())[fetchViewModelClass()]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initDI()
        setTheme()

        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        initData(savedInstanceState)
        initView(savedInstanceState)
    }

    open fun setTheme() {}

    open fun initDI() {}

    open fun initData(savedInstanceState: Bundle?) {}

    open fun initView(savedInstanceState: Bundle?) {}

    abstract fun fetchViewModelClass(): Class<VM>

    abstract fun fetchViewModelFactory(): ViewModelProvider.Factory
}

typealias ActivityBindingInflater<VB> = (LayoutInflater) -> VB