package com.robertconstantindinescu.woutapp.core.util

typealias DefaultApiResource = Resource<Unit>
typealias SimpleResource = Resource<Unit>

sealed class Resource<T>(val data: T? = null, val text: UiText? = null) {
    class Success<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(text: UiText, data: T? = null) : Resource<T>(data, text)
}
