package com.ranstal.tamilfm.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by Bharath on 17-10-2017.
 */
class KotlinUtils {
    companion object {
        fun Context.toast(message: CharSequence?) =
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}