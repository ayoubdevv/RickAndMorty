package yassir.challenge.rickandmorty.presentation.character_list.state

data class CharacterListItem(
    val id: Int,
    val name: String,
    val status: String,
    val gender : String,
    val species: String,
    val imageUrl : String,
)