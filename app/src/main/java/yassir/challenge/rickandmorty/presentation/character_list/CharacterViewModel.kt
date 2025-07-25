package yassir.challenge.rickandmorty.presentation.character_list


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import retrofit2.HttpException
import yassir.challenge.rickandmorty.domain.usecase.GetAllCharacterUseCase
import yassir.challenge.rickandmorty.presentation.character_list.CharacterViewModel.UiEvent.*
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListAction
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListItem
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListState
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterPagingState
import yassir.challenge.rickandmorty.presentation.mapper.toListItem
import java.io.IOException
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

    @OptIn(ExperimentalCoroutinesApi::class)
    val characterPaging: StateFlow<PagingData<CharacterListItem>> = _state
        .map { it.searchQuery }
        .distinctUntilChanged()
        .flatMapLatest { query -> getCharacters(query) }
        .map { pagingData -> pagingData.map { it.toListItem() } }
        .catch {error-> handleException(error) }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Eagerly, PagingData.empty())


    private fun handleException(error: Throwable) {
        val isNetworkError = error is IOException
       val state =  if (error is HttpException && error.code() == 404) {
            CharacterPagingState.Empty
        } else {
            CharacterPagingState.Error("",isNetworkError)
        }
        _state.update { it.copy(characterPagingState = state) }
    }

    fun onAction(action: CharacterListAction) {
        when (action) {
            is CharacterListAction.OnSearchToggle -> onSearchToggle()
            is CharacterListAction.OnSearchQueryChanged -> onSearchQueryChanged(action.query)
            is CharacterListAction.OnItemClicked -> {
                _eventChannel.trySend(NavigateToDetail(action.id))
            }

            CharacterListAction.OnErrorNetworkSettings ->  {
                _eventChannel.trySend(UiEvent.OnErrorNetworkSettings)
            }
            CharacterListAction.OnErrorRetry ->  {
                _eventChannel.trySend(UiEvent.OnErrorRetry)
            }
        }
    }

    private fun onSearchQueryChanged(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    private fun onSearchToggle() {
        _state.update {
            it.copy(
                isSearching = it.isSearching.not(),
                searchQuery = if (it.isSearching) "" else it.searchQuery
            )
        }
    }

    sealed class UiEvent {
        data class NavigateToDetail(val characterId: Int) : UiEvent()
        data object OnErrorNetworkSettings : UiEvent()
        data object OnErrorRetry : UiEvent()
    }
}