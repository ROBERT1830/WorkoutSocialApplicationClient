package com.robertconstantindinescu.woutapp.core.util

import kotlin.jvm.Throws

sealed class UiEvent<T> {
    data class NavigateUp<T>(val params: T?): UiEvent<T>()
    data class NavigateTo<T>(val params: T? = null): UiEvent<T>()
    data class ShowSnackBar<T>(val text: UiText): UiEvent<T>()
}
