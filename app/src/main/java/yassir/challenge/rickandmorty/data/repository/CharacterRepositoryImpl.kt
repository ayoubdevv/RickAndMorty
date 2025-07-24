package yassir.challenge.rickandmorty.data.repository

import yassir.challenge.rickandmorty.data.remote.CharacterApi
import yassir.challenge.rickandmorty.data.remote.toDomain
import yassir.challenge.rickandmorty.domain.module.Character
import yassir.challenge.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(/*private val api: CharacterApi*/) : CharacterRepository {

    override suspend fun getAllCharacter(): List<Character> {
        return FakeCharacterRepository.getCharacters().map { it.toDomain() }
    }
}