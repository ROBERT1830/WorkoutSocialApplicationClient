package com.robertconstantindinescu.woutapp.core.util

import android.content.Context

sealed class UiText {
    //string from api
    data class DynamicString(val text: String): UiText()
    //string from resource id
    data class StringResource(val id: Int): UiText()

    fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> text
            is StringResource -> context.getString(id)
        }
    }
}
