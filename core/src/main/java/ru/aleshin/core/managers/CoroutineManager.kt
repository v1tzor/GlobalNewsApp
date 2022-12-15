package ru.aleshin.core.managers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 14.10.2022.
 */
interface CoroutineManager {

    fun runOnBackground(scope: CoroutineScope, block: CoroutineBlock)

    fun runOnMain(scope: CoroutineScope, block: CoroutineBlock)

    suspend fun changeFlow(isBackground: Boolean = false, block: CoroutineBlock)

    abstract class Abstract(
        private val backgroundDispatcher: CoroutineDispatcher,
        private val mainDispatcher: CoroutineDispatcher
    ) : CoroutineManager {

        override fun runOnBackground(scope: CoroutineScope, block: CoroutineBlock) {
            scope.launch(block = block, context = backgroundDispatcher)
        }

        override fun runOnMain(scope: CoroutineScope, block: CoroutineBlock) {
            scope.launch(block = block, context = mainDispatcher)
        }

        override suspend fun changeFlow(isBackground: Boolean, block: CoroutineBlock) {
            val dispatcher = if (isBackground) backgroundDispatcher else mainDispatcher
            withContext(block = block, context = dispatcher)
        }
    }

    class Base @Inject constructor() : Abstract(
        backgroundDispatcher = Dispatchers.IO,
        mainDispatcher = Dispatchers.Main
    )
}

typealias CoroutineBlock = suspend CoroutineScope.() -> Unit