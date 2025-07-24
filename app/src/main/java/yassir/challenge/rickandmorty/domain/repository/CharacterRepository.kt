package yassir.challenge.rickandmorty.domain.repository

import yassir.challenge.rickandmorty.domain.module.Character

interface CharacterRepository {

    suspend fun getAllCharacter(): List<Character>

    suspend fun getCharacterDetails(characterId : Int): Character

}