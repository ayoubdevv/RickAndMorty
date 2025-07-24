package yassir.challenge.rickandmorty.presentation.character_list.state

import androidx.paging.compose.LazyPagingItems


data class CharacterListState(
    val isLoading: Boolean = false,
    val isSearching: Boolean = false,
    val searchQuery: String = "",
    val characterPagingState: CharacterPagingState = CharacterPagingState.Loading
)


sealed class CharacterPagingState {
    object Loading : CharacterPagingState()
    data class Error(val message: String) : CharacterPagingState()
    data class Success(val characters: LazyPagingItems<CharacterListItem>) : CharacterPagingState()
    data class Preview(val characters: List<CharacterListItem>) : CharacterPagingState()
}