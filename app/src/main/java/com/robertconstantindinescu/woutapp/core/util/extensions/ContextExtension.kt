package com.robertconstantindinescu.woutapp.core.util.extensions

import android.content.Context
import android.content.Intent
import com.robertconstantindinescu.woutapp.R

fun Context.sharePost(postId: String) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(
            Intent.EXTRA_TEXT,
            getString(
                R.string.share_text_for_intent,
                "https://wout-app.com/$postId"
            )
        )
        type = "text/plain"
    }
    if(intent.resolveActivity(packageManager) != null) {
        startActivity(Intent.createChooser(intent, "Select an app to share the post!"))
    }
}