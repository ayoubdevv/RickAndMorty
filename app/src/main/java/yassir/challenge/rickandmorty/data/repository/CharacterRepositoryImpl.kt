package yassir.challenge.rickandmorty.data.repository

import yassir.challenge.rickandmorty.data.dto.CharacterResponseDto
import yassir.challenge.rickandmorty.data.remote.CharacterApi
import yassir.challenge.rickandmorty.data.remote.toDomain
import yassir.challenge.rickandmorty.domain.module.Character
import yassir.challenge.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val api: CharacterApi) : CharacterRepository {

    override suspend fun getAllCharacter(): List<Character> {
       // return FakeCharacterRepository.getCharacters().map { it.toDomain() }

       return api.getCharacterList(1).results.map { it.toDomain() }

    }

    override suspend fun getCharacterList(page: Int,query : String?): CharacterResponseDto {
        // return FakeCharacterRepository.getCharacters().map { it.toDomain() }

        return api.getCharacterList(page = page, query = query)

    }

    override suspend fun getCharacterDetails(characterId: Int): Character {


      /*  return FakeCharacterRepository.getCharacters().first {
            it.id == characterId
        }.toDomain()*/

        return api.getCharacterDetails(characterId).toDomain()
    }

}
