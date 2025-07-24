package yassir.challenge.rickandmorty.domain.module

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val gender: String,
    val species: String,
    val image : String,
)