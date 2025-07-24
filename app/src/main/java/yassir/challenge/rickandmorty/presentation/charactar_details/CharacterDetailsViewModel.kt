package yassir.challenge.rickandmorty.presentation.charactar_details

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yassir.challenge.rickandmorty.domain.module.Character
import yassir.challenge.rickandmorty.domain.usecase.GetCharacterDetailsUseCase
import yassir.challenge.rickandmorty.domain.util.DataError
import yassir.challenge.rickandmorty.domain.util.DataError.Network.*
import yassir.challenge.rickandmorty.presentation.Destination
import yassir.challenge.rickandmorty.presentation.charactar_details.state.CharacterDetailsState
import yassir.challenge.rickandmorty.presentation.mapper.toDetails
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private val detailsUseCase: GetCharacterDetailsUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(CharacterDetailsState())
    val state: StateFlow<CharacterDetailsState> get() = _state.asStateFlow()


    val details =  stateHandle.toRoute<Destination.CharacterDetails>()


    init {
        loadCharacterDetails()
    }


    private fun loadCharacterDetails() {
        viewModelScope.launch {

            val result = detailsUseCase(details.characterId)
            result.handleResult(::onSuccess, ::onFailure)
        }
    }

    private fun onSuccess(character: Character) {
        _state.update { it.copy(detailItem = character.toDetails(), isLoading = false) }
    }

    private fun onFailure(error: DataError.Network) {
        when(error) {
            Unknown -> TODO()
        }
    }


}