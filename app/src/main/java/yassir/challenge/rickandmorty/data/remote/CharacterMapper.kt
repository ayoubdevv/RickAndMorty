package yassir.challenge.rickandmorty.data.remote

import yassir.challenge.rickandmorty.data.dto.CharacterDto
import yassir.challenge.rickandmorty.domain.module.Character
import yassir.challenge.rickandmorty.domain.module.Location
import yassir.challenge.rickandmorty.domain.module.Origin


fun CharacterDto.toDomain(): Character {
    return Character(
        id = id,
        name = name,
        status = status,
        gender = gender,
        species = species,
        image = image,
        origin = Origin(origin.name, origin.url),
        location = Location(location.name, location.url),
        episode = episode
    )
}