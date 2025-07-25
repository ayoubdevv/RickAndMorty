package yassir.challenge.rickandmorty.presentation.charactar_details

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.toRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yassir.challenge.rickandmorty.R
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
      val stateHandle: SavedStateHandle,
    private val detailsUseCase: GetCharacterDetailsUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(CharacterDetailsState())
    val state: StateFlow<CharacterDetailsState> get() = _state.asStateFlow()


    val details = stateHandle.toRoute<Destination.CharacterDetails>()



    fun onRetry() {
        loadCharacterDetails()
    }

      fun loadCharacterDetails(characterId : Int = details.characterId) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            delay(250) // to Sho
            val result = detailsUseCase(characterId)
            result.handleResult(::onSuccess, ::onFailure)
        }
    }

    private fun onSuccess(character: Character) {
        _state.update { it.copy(detailItem = character.toDetails(), isLoading = false, errorMessage = null) }
    }

    private fun onFailure(error: DataError.Network) {
        val (messageResId, isNetworkError) = when (error) {
            NETWORK_UNAVAILABLE -> R.string.error_no_internet to true
            UNAVAILABLE -> R.string.no_results_title to false
            SERVER_ERROR -> R.string.error_server_error to false
            Unknown -> R.string.error_unknown to false
        }

        _state.update {
            it.copy(
                isLoading = false,
                isNetworkError = isNetworkError,
                errorMessage = messageResId
            )
        }
    }


}