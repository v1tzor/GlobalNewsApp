package ru.aleshin.core.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

/**
 * @author Stanislav Aleshin on 16.10.2022.
 */

fun <T> Flow<T>.lifecycleCollect(
    viewLifecycleOwner: LifecycleOwner,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    collector: FlowCollector<T>
) = with(viewLifecycleOwner) {
    lifecycleScope.launch {
        this@lifecycleCollect.flowWithLifecycle(lifecycle, state).collect(collector)
    }
}