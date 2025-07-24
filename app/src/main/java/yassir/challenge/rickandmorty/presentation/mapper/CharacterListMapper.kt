package yassir.challenge.rickandmorty.presentation.mapper

import yassir.challenge.rickandmorty.domain.module.Character
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListItem

fun Character.toUi(): CharacterListItem {
    return CharacterListItem(
        id = id,
        name = name,
        status = status,
        gender = gender,
        species = species,
        imageUrl = image
    )
}