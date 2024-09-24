package com.techtest.daffa_github_user.util

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

fun ImageView.setImageUrl(
    c: Context,
    imageUrl: String?,
    progressBar: ProgressBar,
    errorResourceId: Int
) {
    if (!isValidContext(c)) return


    progressBar.visible()
    Glide.with(c)
        .load(imageUrl)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.gone()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: com.bumptech.glide.load.DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                progressBar.gone()
                return false
            }
        })
        .error(errorResourceId)
        .into(this)
}

fun isValidContext(context: Context): Boolean {
    val activity = context as? Activity
    return if (activity != null) {
        !(activity.isDestroyed || activity.isFinishing)
    } else {
        true
    }
}

fun View.setVisibility(visibility: Boolean) {
    this.visibility = if (visibility) View.VISIBLE else View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}