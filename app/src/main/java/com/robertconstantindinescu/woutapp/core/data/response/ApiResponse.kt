package com.robertconstantindinescu.woutapp.core.data.response

import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.util.DefaultApiResource
import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.core.util.UiText

data class ApiResponse<T>(
    val successful: Boolean,
    //message for error. Could be nullable
    val message: String? = null,
    //data to be sent to client. Could be null fi there is an error
    val data: T? = null
) {
    inline fun <R> mapApiResponse(transformation: (T) -> R): Resource<R> {
        return when (this.successful) {
            true -> {
                Resource.Success(this.data?.let { apiData -> transformation.invoke(apiData) })
            }
            false -> {
                this.message?.let {
                    Resource.Error(UiText.DynamicString(this.message))
                } ?: Resource.Error(UiText.StringResource(com.robertconstantindinescu.woutapp.R.string.unknown_error))
            }
        }
    }

}

