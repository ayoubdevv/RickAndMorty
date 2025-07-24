package yassir.challenge.rickandmorty.data.remote.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import yassir.challenge.rickandmorty.data.remote.toDomain
import yassir.challenge.rickandmorty.domain.module.Character
import yassir.challenge.rickandmorty.domain.repository.CharacterRepository
import java.io.IOException


data class CharacterPagingSource(val repository: CharacterRepository, val query: String?) :
    PagingSource<Int, Character>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {

        return try {
            val currentPage = params.key ?: 1
            val result = repository.getCharacterList(currentPage, query)

            val nextPage = if (result.info.next == null) null else currentPage + 1
            val prevPage = if (result.info.prev == null) null else currentPage - 1

            LoadResult.Page(
                data = result.results.map { it.toDomain() },
                prevKey = prevPage,
                nextKey = nextPage
            )
        } catch (exception: IOException) {
            exception.printStackTrace()
            return LoadResult.Error(exception)
        } catch (exception: retrofit2.HttpException) {
            exception.printStackTrace()
            return LoadResult.Error(exception)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }


    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.minus(1) ?: anchorPage?.nextKey?.plus(1)
        }
    }

}