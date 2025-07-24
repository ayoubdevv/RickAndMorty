package yassir.challenge.rickandmorty.presentation.charactar_details.state

data class CharacterDetailItem(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val imageUrl: String,
    val origin: String,
    val location: String,
    val episodeCount: Int
)