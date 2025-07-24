package yassir.challenge.rickandmorty.presentation.character_list


import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListAction
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListState
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(

    private val savedStateHandle: SavedStateHandle,

    ) : ViewModel() {


    private val _state = MutableStateFlow(CharacterListState())
    val state: StateFlow<CharacterListState> get() = _state.asStateFlow()


    fun onAction(action: CharacterListAction) {

    }
}