package com.example.to_docompose.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

object ToastManager {

    fun showShort(
        context: Context,
        @StringRes messageId: Int,
    ) {
        Toast.makeText(context, messageId, Toast.LENGTH_SHORT).show()
    }
}
