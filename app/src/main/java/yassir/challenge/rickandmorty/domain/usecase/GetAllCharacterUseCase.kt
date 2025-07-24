package yassir.challenge.rickandmorty.domain.usecase


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yassir.challenge.rickandmorty.domain.module.Character
import yassir.challenge.rickandmorty.domain.repository.CharacterRepository
import yassir.challenge.rickandmorty.domain.util.DataError
import yassir.challenge.rickandmorty.domain.util.Result
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GetAllCharacterUseCase @Inject constructor(private val repository: CharacterRepository) {

    suspend operator fun invoke(): Result<List<Character>, DataError.Network> {

        return withContext(Dispatchers.IO) {
            try {
                val result = repository.getAllCharacter()
                Result.Success(result)
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Failure(DataError.Network.Unknown)
            }
        }
    }

}