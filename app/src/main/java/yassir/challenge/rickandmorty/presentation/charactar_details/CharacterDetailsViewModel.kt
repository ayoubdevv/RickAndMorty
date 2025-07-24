package yassir.challenge.rickandmorty.presentation.charactar_details

import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import yassir.challenge.rickandmorty.presentation.charactar_details.state.CharacterDetailsState
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor() : ViewModel() {


    private val _state  = MutableStateFlow(CharacterDetailsState())
    val state: StateFlow<CharacterDetailsState> get() = _state.asStateFlow()

}