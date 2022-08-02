package com.robertconstantindinescu.woutapp.core.util

class PaginatorImpl<T>(
    private val onLoad: (nextLoad: Boolean) -> Unit,
    private val onRequest: suspend (nextPage: Int) -> Resource<List<T>>,
    private val onSuccess: (items: List<T>) -> Unit,
    private val onError: suspend (text: UiText) -> Unit //suspend because we are using channel which is a suspend function as well
) : Paginator {

    private var page = 0

    override suspend fun loadNextPosts() {
        onLoad(true)
        when (val result = onRequest(page)) {
            is Resource.Success -> {
                page++
                //if no data cames then means that we reached the end of the list and an emptyList will
                //be sended
                onSuccess(result.data ?: emptyList())
                onLoad(false)
            }
            is Resource.Error -> {
                onError(result.text ?: UiText.unknownError())
                onLoad(false)
            }

        }
    }
}