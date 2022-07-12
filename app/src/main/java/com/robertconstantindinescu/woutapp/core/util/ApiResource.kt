package com.robertconstantindinescu.woutapp.core.util

typealias DefaultApiResource = ApiResource<Unit>

sealed class ApiResource<T>(val data: T? = null, val text: UiText? = null) {
    class Success<T>(data: T?) : ApiResource<T>(data)
    class Error<T>(text: UiText?, data: T? = null) : ApiResource<T>(data, text)
}
