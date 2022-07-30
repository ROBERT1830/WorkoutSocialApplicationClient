package com.robertconstantindinescu.woutapp.core.util

interface Paginator {
    suspend fun loadNextPosts()
}