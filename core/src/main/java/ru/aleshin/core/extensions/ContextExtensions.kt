package ru.aleshin.core.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * @author Stanislav Aleshin on 26.11.2022.
 */

fun Context.openUrl(url: String) = startActivity(
    Intent(Intent.ACTION_VIEW, Uri.parse(url))
)