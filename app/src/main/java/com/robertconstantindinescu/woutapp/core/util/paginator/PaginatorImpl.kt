package com.robertconstantindinescu.woutapp.core.util.paginator

import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.core.util.UiText

class PaginatorImpl<Key, Item>(
    private val initialKey: Key,
    private inline val onLoad: (nextLoad: Boolean) -> Unit,
    private inline val onRequest: suspend (nextPage: Key) -> Resource<List<Item>>,
    private inline val getNextKey: () -> Key,
    private inline val onSuccess: (items: List<Item>, newKey: Key) -> Unit,
    private inline val onError: suspend (text: UiText) -> Unit //suspend because we are using channel which is a suspend function as well
) : Paginator {

    private var currentPage = initialKey

    override suspend fun loadNextPosts() {
        //if (resetPaginator) { page = 0}
        onLoad(true)
        when (val result = onRequest(currentPage)) {
            is Resource.Success -> {
                //if no data cames then means that we reached the end of the list and an emptyList will
                //be sent
                onSuccess(result.data ?: emptyList(), currentPage)
                currentPage = getNextKey()

                onLoad(false)
            }
            is Resource.Error -> {
                onError(result.text ?: UiText.unknownError())
                onLoad(false)
            }
        }
    }

    override fun reset() {
        currentPage = initialKey //0 by default from state
    }
}