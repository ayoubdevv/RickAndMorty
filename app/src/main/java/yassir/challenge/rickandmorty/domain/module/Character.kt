package yassir.challenge.rickandmorty.domain.module

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val gender: String,
    val species: String,
    val image : String,
    val origin: Origin,
    val location: Location,
    val episode: List<String>,
)


data class Location(
    val name: String,
    val url: String
)

data class Origin(
    val name: String,
    val url: String
)
