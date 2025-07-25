package yassir.challenge.rickandmorty.domain.usecase


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import yassir.challenge.rickandmorty.data.remote.source.CharacterPagingSource
import yassir.challenge.rickandmorty.domain.module.Character
import yassir.challenge.rickandmorty.domain.repository.CharacterRepository
import yassir.challenge.rickandmorty.domain.util.DataError
import yassir.challenge.rickandmorty.domain.util.Result
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GetAllCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {

    operator fun invoke(query: String? = null): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { CharacterPagingSource(repository, query) }
        ).flow.flowOn(Dispatchers.IO)
    }
}