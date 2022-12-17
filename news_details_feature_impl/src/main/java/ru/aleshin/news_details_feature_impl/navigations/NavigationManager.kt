package ru.aleshin.news_details_feature_impl.navigations

import android.content.Intent
import android.net.Uri
import ru.aleshin.core.navigations.GlobalRouter
import javax.inject.Inject

/**
 * @author Stanislav Aleshin on 29.11.2022.
 */
internal interface NavigationManager {

    fun navigateToSharingScreen(newsUrl: String)

    fun navigateToNewsUrl(newsUrl: String)

    fun navigateToBackScreen()

    class Base @Inject constructor(
        private val globalRouter: GlobalRouter
    ) : NavigationManager {

        override fun navigateToSharingScreen(newsUrl: String) {
            val sharingIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, newsUrl)
            }
            globalRouter.showIntent(Intent.createChooser(sharingIntent, "Share"))
        }

        override fun navigateToNewsUrl(newsUrl: String) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newsUrl))
            globalRouter.showIntent(intent)
        }

        override fun navigateToBackScreen() {
            globalRouter.exit()
        }
    }
}