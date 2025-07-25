package yassir.challenge.rickandmorty.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import yassir.challenge.rickandmorty.domain.module.Character
import yassir.challenge.rickandmorty.domain.repository.CharacterRepository
import yassir.challenge.rickandmorty.domain.util.DataError
import yassir.challenge.rickandmorty.domain.util.Result
import java.io.IOException
import javax.inject.Inject


class GetCharacterDetailsUseCase @Inject constructor(private val repository: CharacterRepository) {

    suspend operator fun invoke(characterId: Int): Result<Character, DataError.Network> {

        return withContext(Dispatchers.IO) {
            try {
                val character = repository.getCharacterDetails(characterId)
                Result.Success(character)
            } catch (e: HttpException) {
                val error = when (e.code()) {
                    404 -> DataError.Network.UNAVAILABLE
                    else -> DataError.Network.SERVER_ERROR
                }
                Result.Failure(error)
            } catch (e: IOException) {
                e.printStackTrace()
                Result.Failure(DataError.Network.NETWORK_UNAVAILABLE)
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Failure(DataError.Network.Unknown)
            }
        }
    }

}