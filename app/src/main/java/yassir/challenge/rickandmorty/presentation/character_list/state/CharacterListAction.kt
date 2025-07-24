package yassir.challenge.rickandmorty.presentation.character_list.state

sealed class CharacterListAction {

    data class OnItemClicked(val id: Int) : CharacterListAction()

    data object OnSearchToggle : CharacterListAction()

    data class SnSearchQueryChanged(val query : String) : CharacterListAction()

}