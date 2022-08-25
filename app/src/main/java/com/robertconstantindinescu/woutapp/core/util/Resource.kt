package com.robertconstantindinescu.woutapp.core.util

import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.data.response.ApiResponse

typealias DefaultApiResource = Resource<Unit>


sealed class Resource<T>(val data: T? = null, val text: UiText? = null) {
    class Success<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(text: UiText, data: T? = null) : Resource<T>(data, text)

    inline fun <R> mapResourceData(success: (T?) -> R, error: (text: UiText?, data: T?) -> R): R {
        return when(this) {
            is Success -> success(this.data)
            is Error -> error(this.text, this.data)
        }
    }
}

