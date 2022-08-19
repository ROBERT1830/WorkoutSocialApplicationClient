package com.robertconstantindinescu.woutapp.core.util.paginator

interface Paginator {
    suspend fun loadNextPosts()
    fun reset()
}