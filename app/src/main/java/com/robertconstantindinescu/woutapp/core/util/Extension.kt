package com.robertconstantindinescu.woutapp.core.util

import com.robertconstantindinescu.woutapp.core.data.response.BasicApiResponse
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

inline fun <T> callApi(call: () -> Response<T>): BasicApiResponse<T> {
    return try {
        val response = call.invoke()
        when {
            response.isSuccessful && response.body() != null -> BasicApiResponse(
                successful = true,
                data = response.body()
            )
            else -> BasicApiResponse(
                successful = false,
                message = response.message()
            )
        }
    }catch (exception: IOException) {
        BasicApiResponse(
            successful = false,
            message = exception.message
        )
    }catch (exception: HttpException) {
        BasicApiResponse(
            successful = false,
            message = exception.message
        )
    }
}