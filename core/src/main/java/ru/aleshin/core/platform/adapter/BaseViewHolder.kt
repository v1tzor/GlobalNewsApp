package ru.aleshin.core.platform.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * @author Stanislav Aleshin on 15.10.2022.
 */
abstract class BaseViewHolder<I>(
    viewBinding: ViewBinding
) : RecyclerView.ViewHolder(viewBinding.root) {

    open fun bind(data: I) {}

    open fun bind(data: I, payload: List<Any>) {}
}
