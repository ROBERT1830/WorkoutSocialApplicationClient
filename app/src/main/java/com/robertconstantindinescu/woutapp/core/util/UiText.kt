package com.robertconstantindinescu.woutapp.core.util

import android.content.Context
import com.robertconstantindinescu.woutapp.R

const val UNKNOWN_ERROR = "Unknown Error"
sealed class UiText {
    //string from api
    data class DynamicString(val text: String?): UiText()
    //string from resource id
    data class StringResource(val id: Int): UiText()

    fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> text ?: UNKNOWN_ERROR
            is StringResource -> context.getString(id)
        }
    }
    fun unknownError(): UiText {
        return StringResource(R.string.error_unknown)
    }
}
