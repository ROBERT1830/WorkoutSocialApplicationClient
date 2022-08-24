package com.robertconstantindinescu.woutapp.core.util

import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.data.response.ApiResponse

typealias DefaultApiResource = Resource<Unit>


sealed class Resource<T>(val data: T? = null, val text: UiText? = null) {
    class Success<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(text: UiText, data: T? = null) : Resource<T>(data, text)

//    companion object {
//        fun <T> simpleApiResponseCheck(response: ApiResponse<T>): DefaultApiResource {
//            return when (response.successful) {
//                true -> {
//                    Resource.Success<Unit>()
//                }
//                false -> {
//                    response.message?.let {
//                        Resource.Error(UiText.DynamicString(response.message))
//                    } ?: Resource.Error(UiText.StringResource(R.string.unknown_error))
//                }
//            }
//        }
//    }

}

