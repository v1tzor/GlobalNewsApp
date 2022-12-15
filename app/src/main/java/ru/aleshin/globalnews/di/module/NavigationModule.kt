package ru.aleshin.globalnews.di.module

import com.github.terrakok.cicerone.Cicerone
import dagger.Module
import dagger.Provides
import ru.aleshin.core.di.AppScope
import ru.aleshin.core.navigations.BottomNavRouter
import ru.aleshin.core.navigations.GlobalRouter
import ru.aleshin.globalnews.di.annotation.GlobalCiceroneHolder
import ru.aleshin.globalnews.di.annotation.NavCiceroneHolder

/**
 * @author Stanislav Aleshin on 28.10.2022.
 */
@Module
class NavigationModule {

    @[Provides AppScope]
    fun provideGlobalCicerone() = Cicerone.create(GlobalRouter())

    @[Provides AppScope GlobalCiceroneHolder]
    fun provideGlobalNavigatorHolder(cicerone: Cicerone<GlobalRouter>) = cicerone.getNavigatorHolder()

    @[Provides AppScope]
    fun provideGlobalRouter(cicerone: Cicerone<GlobalRouter>) = cicerone.router

    @[Provides AppScope]
    fun provideNavCicerone() = Cicerone.create(BottomNavRouter())

    @[Provides AppScope NavCiceroneHolder]
    fun provideNavNavigatorHolder(cicerone: Cicerone<BottomNavRouter>) = cicerone.getNavigatorHolder()

    @[Provides AppScope]
    fun provideNavRouter(cicerone: Cicerone<BottomNavRouter>) = cicerone.router
}