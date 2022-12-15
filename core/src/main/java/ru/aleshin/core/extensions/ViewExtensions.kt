package ru.aleshin.core.extensions

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar

/**
 * @author Stanislav Aleshin on 01.11.2022.
 */

fun ImageView.loadImage(
    urlToImage: String,
    @DrawableRes errorIcon: Int
) {
    Glide.with(context)
        .load(urlToImage)
        .centerCrop()
        .error(AppCompatResources.getDrawable(context, errorIcon))
        .into(this)
}

fun ImageView.setImageDrawableRes(@DrawableRes image: Int) {
    this.setImageDrawable(AppCompatResources.getDrawable(context, image))
}

fun RadioGroup.checkedRadioButtonTag(): String {
    return findViewById<RadioButton>(checkedRadioButtonId).tag.toString()
}

fun RadioGroup.checkByTag(tag: String) {
    check(findViewWithTag<RadioButton>(tag).id)
}

fun RadioGroup.addRadioButton(
    @StringRes title: Int,
    tag: String,
    paddingVertical: Int = 36
) = addView(
    RadioButton(context).apply {
        this.setText(title)
        this.tag = tag
        setPadding(0, paddingVertical, 0, paddingVertical)
    },
    ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
)

fun ChipGroup.checkedChipButtonTag(): String {
    return findViewById<Chip>(checkedChipId).tag.toString()
}

fun ChipGroup.checkByTag(tag: String) {
    check(findViewWithTag<Chip>(tag).id)
}

fun ChipGroup.addChip(
    @StringRes title: Int,
    tag: String
) = addView(
    Chip(context).apply {
        this.setText(title)
        this.tag = tag
    },
    ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
)

fun View.showSnackBar(@StringRes text: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(context, this, context.getText(text), duration).show()
}

fun View.showSnackBar(text: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(context, this, text, duration).show()
}