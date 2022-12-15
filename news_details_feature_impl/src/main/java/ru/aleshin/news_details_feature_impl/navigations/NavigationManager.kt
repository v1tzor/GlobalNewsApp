package ru.aleshin.news_details_feature_impl.navigations

import ru.aleshin.core.navigations.GlobalRouter
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
internal interface NavigationManager {

    fun navigateToBackScreen()

    class Base @Inject constructor(
        private val globalRouter: GlobalRouter
    ) : NavigationManager {

        override fun navigateToBackScreen() {
            globalRouter.exit()
        }
    }
}