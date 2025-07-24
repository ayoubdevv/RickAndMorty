package yassir.challenge.rickandmorty.data.remote

import yassir.challenge.rickandmorty.data.dto.CharacterDto
import yassir.challenge.rickandmorty.domain.module.Character


fun CharacterDto.toDomain(): Character {
    return Character(
        id = id,
        name = name,
        status = status,
        species = species,
        image = image,
    )
}