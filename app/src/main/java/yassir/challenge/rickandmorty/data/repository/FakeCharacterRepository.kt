package yassir.challenge.rickandmorty.data.repository

import yassir.challenge.rickandmorty.data.dto.CharacterDto
import yassir.challenge.rickandmorty.data.dto.Location
import yassir.challenge.rickandmorty.data.dto.Origin

object FakeCharacterRepository {

    fun getCharacters(): List<CharacterDto> {
        return listOf(
            CharacterDto(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                type = "",
                gender = "Male",
                origin = Origin(
                    name = "Earth (C-137)",
                    url = "https://rickandmortyapi.com/api/location/1"
                ),
                location = Location(
                    name = "Citadel of Ricks",
                    url = "https://rickandmortyapi.com/api/location/3"
                ),
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                episode = listOf(
                    "https://rickandmortyapi.com/api/episode/1",
                    "https://rickandmortyapi.com/api/episode/2"
                ),
                url = "https://rickandmortyapi.com/api/character/1",
                created = "2017-11-04T18:48:46.250Z"
            ),
            CharacterDto(
                id = 2,
                name = "Morty Smith",
                status = "Alive",
                species = "Human",
                type = "",
                gender = "Male",
                origin = Origin(
                    name = "Earth (C-137)",
                    url = "https://rickandmortyapi.com/api/location/1"
                ),
                location = Location(
                    name = "Earth (Replacement Dimension)",
                    url = "https://rickandmortyapi.com/api/location/20"
                ),
                image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
                episode = listOf(
                    "https://rickandmortyapi.com/api/episode/1",
                    "https://rickandmortyapi.com/api/episode/2"
                ),
                url = "https://rickandmortyapi.com/api/character/2",
                created = "2017-11-04T18:50:21.651Z"
            )
            // Add more characters if needed
        )
    }
}
