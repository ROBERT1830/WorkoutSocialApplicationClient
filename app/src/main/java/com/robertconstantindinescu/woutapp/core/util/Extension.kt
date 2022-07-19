package com.robertconstantindinescu.woutapp.core.util

import android.content.Context
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.data.response.BasicApiResponse
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

inline fun <T> callApi(call: () -> Response<T>): ApiResource<T> {
    return try {
        val response = call.invoke()
        when {
            response.isSuccessful && response.body() != null -> ApiResource.Success(data = response.body())
            else -> ApiResource.Error(response.message())
        }
    }catch (exception: IOException) {
        ApiResource.Error(exception.message)
    }catch (exception: HttpException) {
        ApiResource.Error(exception.message)

    }
}