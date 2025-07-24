package yassir.challenge.rickandmorty.domain.usecase


import yassir.challenge.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject


class GetAllCharacterUseCase @Inject constructor(private val repository: CharacterRepository) {

    suspend operator fun invoke() {

    }

}