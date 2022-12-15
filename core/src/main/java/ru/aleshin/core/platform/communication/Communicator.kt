package ru.aleshin.core.platform.communication

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import ru.aleshin.core.extensions.lifecycleCollect

/**
 * @author Stanislav Aleshin on 14.10.2022.
 */
interface Communicator {

    interface Update<T> {
        fun update(value: T)
    }

    interface Collect<T> {
        fun collect(lifecycleOwner: LifecycleOwner, collector: FlowCollector<T>)
    }

    interface Read<T> {
        suspend fun read(): T
    }

    interface Combined<T> : Update<T>, Collect<T>, Read<T>

    abstract class AbstractStateFlow<T>(
        defualtValue: T,
        private val lifecycleState: Lifecycle.State = Lifecycle.State.STARTED
    ) : Combined<T> {

        private val flow = MutableStateFlow(defualtValue)

        override fun update(value: T) {
            flow.tryEmit(value)
        }

        override fun collect(
            lifecycleOwner: LifecycleOwner,
            collector: FlowCollector<T>
        ) {
            flow.lifecycleCollect(lifecycleOwner, lifecycleState, collector)
        }

        override suspend fun read() = flow.value
    }

    abstract class AbstractSharedFlow<T>(
        flowReplay: Int = 1,
        flowBufferCapacity: Int = 0,
        flowBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND,
        private val lifecycleState: Lifecycle.State = Lifecycle.State.STARTED
    ) : Combined<T> {

        private val flow = MutableSharedFlow<T>(flowReplay, flowBufferCapacity, flowBufferOverflow)

        override fun update(value: T) {
            flow.tryEmit(value)
        }

        override fun collect(
            lifecycleOwner: LifecycleOwner,
            collector: FlowCollector<T>
        ) {
            flow.lifecycleCollect(lifecycleOwner, lifecycleState, collector)
        }

        override suspend fun read() = flow.first()
    }

    class EmptyStateCommunicator : AbstractStateFlow<Unit>(Unit)
}