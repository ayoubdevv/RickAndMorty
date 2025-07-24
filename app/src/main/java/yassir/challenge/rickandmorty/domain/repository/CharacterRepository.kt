package yassir.challenge.rickandmorty.domain.repository

import yassir.challenge.rickandmorty.data.dto.CharacterResponseDto
import yassir.challenge.rickandmorty.domain.module.Character

interface CharacterRepository {

    suspend fun getAllCharacter(): List<Character>

    suspend fun getCharacterList(page: Int, query: String?): CharacterResponseDto

    suspend fun getCharacterDetails(characterId: Int): Character

}