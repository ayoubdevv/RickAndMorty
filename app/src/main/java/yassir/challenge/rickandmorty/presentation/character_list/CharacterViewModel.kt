package yassir.challenge.rickandmorty.presentation.character_list


import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yassir.challenge.rickandmorty.domain.module.Character
import yassir.challenge.rickandmorty.domain.usecase.GetAllCharacterUseCase
import yassir.challenge.rickandmorty.domain.util.DataError
import yassir.challenge.rickandmorty.domain.util.DataError.Network.*
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListAction
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListState
import yassir.challenge.rickandmorty.presentation.mapper.toListItem
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getCharacters: GetAllCharacterUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(CharacterListState())
    val state: StateFlow<CharacterListState> get() = _state.asStateFlow()


    private val _eventChannel = Channel<UiEvent>()
    val events = _eventChannel.receiveAsFlow()

    init {
        loadCharacters()
    }

    fun onAction(action: CharacterListAction) {
        when (action) {
            is CharacterListAction.OnSearchToggle -> onSearchToggle()
            is CharacterListAction.SnSearchQueryChanged -> onSearchQueryChanged(action.query)
            is CharacterListAction.OnItemClicked -> {
               _eventChannel.trySend(UiEvent.NavigateToDetail(action.id))
            }
        }
    }

    private fun onSearchQueryChanged(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    private fun onSearchToggle() {
        _state.update { it.copy(isSearching = it.isSearching.not()) }
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            val result = getCharacters()
            result.handleResult(onSuccess = ::onSuccess, onFailure = ::onFailure)
        }
    }


    private fun onSuccess(characters: List<Character>) {
        _state.update { state -> state.copy(characterList = characters.map { it.toListItem() }) }
    }

    private fun onFailure(error: DataError.Network) {
        when (error) {
            Unknown -> TODO()
        }
    }

    sealed class UiEvent {
        data class NavigateToDetail(val characterId: Int) : UiEvent()
    }
}