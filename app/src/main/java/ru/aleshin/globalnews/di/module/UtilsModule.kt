package ru.aleshin.globalnews.di.module

import dagger.Binds
import dagger.Module
import ru.aleshin.core.managers.CoroutineManager
import ru.aleshin.globalnews.navigation.AppNavigationManager

/**
 * @author Stanislav Aleshin on 28.10.2022.
 */
@Module
interface UtilsModule {

    @Binds
    fun provideAppNavigationManager(manager: AppNavigationManager.Base): AppNavigationManager

    @Binds
    fun provideCoroutineManger(manager: CoroutineManager.Base): CoroutineManager
}