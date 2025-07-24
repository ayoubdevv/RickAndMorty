package yassir.challenge.rickandmorty.presentation.mapper

import yassir.challenge.rickandmorty.domain.module.Character
import yassir.challenge.rickandmorty.presentation.charactar_details.state.CharacterDetailItem


fun Character.toDetails(): CharacterDetailItem {
    return CharacterDetailItem(
        id = id,
        name = name,
        status = status,
        gender = gender,
        species = species,
        imageUrl = image,
        origin = origin.name,
        location = location.name,
        episodeCount = episode.size
    )
}
