package com.robertconstantindinescu.woutapp.core.util

import com.robertconstantindinescu.woutapp.core.data.response.ApiResponse
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

inline fun <T> callApi(call: () -> Response<T>): ApiResponse<T> {
    return try {
        val response = call.invoke()
        when {
            response.isSuccessful && response.body() != null -> ApiResponse(
                successful = true,
                data = response.body()
            )
            else -> ApiResponse(
                successful = false,
                message = response.message()
            )
        }
    }catch (exception: IOException) {
        ApiResponse(
            successful = false,
            message = exception.message
        )
    }catch (exception: HttpException) {
        ApiResponse(
            successful = false,
            message = exception.message
        )
    }
}