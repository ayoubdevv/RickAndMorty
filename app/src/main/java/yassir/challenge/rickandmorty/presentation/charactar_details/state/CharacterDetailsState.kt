package yassir.challenge.rickandmorty.presentation.charactar_details.state




data class CharacterDetailsState(
    val isLoading: Boolean = false,
    val detailItem: CharacterDetailItem ? = null,
    val errorMessage : Int? = null,
    val isNetworkError: Boolean = false
) {
    val hasError = errorMessage != null
}