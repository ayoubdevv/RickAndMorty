package yassir.challenge.rickandmorty.presentation.character_list.state

sealed class CharacterListAction {

    data class OnItemClicked(val id: Int) : CharacterListAction()

    data object OnSearchToggle : CharacterListAction()

    data class OnSearchQueryChanged(val query : String) : CharacterListAction()

    data object OnErrorRetry : CharacterListAction()

    data object OnErrorNetworkSettings : CharacterListAction()



}