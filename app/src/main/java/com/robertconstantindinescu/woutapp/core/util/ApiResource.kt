package com.robertconstantindinescu.woutapp.core.util

typealias DefaultApiResource = ApiResource<Unit>

sealed class ApiResource<T>(val data: T? = null, val text: String? = null) {
    class Success<T>(data: T?) : ApiResource<T>(data)
    class Error<T>(text: String?, data: T? = null) : ApiResource<T>(data, text)
}
