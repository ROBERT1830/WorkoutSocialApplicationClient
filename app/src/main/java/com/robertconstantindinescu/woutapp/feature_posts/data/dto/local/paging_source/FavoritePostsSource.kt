package com.robertconstantindinescu.woutapp.feature_posts.data.dto.local.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.robertconstantindinescu.woutapp.feature_posts.data.dto.local.entities.FavoritePostEntity
import com.robertconstantindinescu.woutapp.feature_posts.data.dto.local.entities.FavoritesPostDao
import javax.inject.Inject

//class FavoritePostsSource(
//    private val dao: FavoritesPostDao
//) : PagingSource<Int, FavoritePostEntity>() {
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FavoritePostEntity> {
//        val page = params.key ?: 0
//
//        return try {
//            val entities = dao.getAllFavoritesPosts(params.loadSize, page * params.loadSize)
//
//            LoadResult.Page(
//                data = entities,
//                prevKey = if (page == 0) null else page - 1,
//                nextKey = if (entities.isEmpty()) null else page + 1
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//}