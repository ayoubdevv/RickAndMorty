package yassir.challenge.rickandmorty.presentation.character_list.state


data class CharacterListState(
    val isLoading: Boolean = false,
    val isSearching : Boolean = false,
    val searchQuery : String = "",
    val characterList: List<CharacterListItem> = emptyList()
)