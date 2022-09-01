package com.robertconstantindinescu.woutapp.core.util.paginator

import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.core.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

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
        onLoad(true)

        onRequest(currentPage).mapResourceData(
            success = { newDataList ->
                //if no data cames then means that we reached the end of the list and an emptyList will
                //be sent
                onSuccess(newDataList ?: emptyList(), currentPage)
                currentPage = getNextKey()

                onLoad(false)
            },
            error = { text, _ ->
                onError(text ?: UiText.unknownError())
                onLoad(false)
            }
        )
    }

    override fun reset() {
        currentPage = initialKey //0 by default from state
    }
}


class PaginatorImpl2<Key, Item>(
    private val initialKey: Key,
    private inline val onLoad: (nextLoad: Boolean) -> Unit,
    private inline val onRequest: suspend (nextPage: Key) -> Flow<List<Item>>,
    private inline val getNextKey: () -> Key,
    private inline val onSuccess: (items: List<Item>, newKey: Key) -> Unit,
    private inline val onError: suspend (text: UiText) -> Unit = {} //suspend because we are using channel which is a suspend function as well
) : Paginator {

    private var currentPage = initialKey

    override suspend fun loadNextPosts() {
        onLoad(true)

        (onRequest(currentPage).collectLatest {
                onSuccess(it, currentPage)
                currentPage = getNextKey()
                onLoad(false)
        })
    }

    override fun reset() {
        currentPage = initialKey //0 by default from state
    }

}