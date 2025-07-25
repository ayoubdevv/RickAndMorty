package yassir.challenge.rickandmorty

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import yassir.challenge.rickandmorty.data.remote.toDomain
import yassir.challenge.rickandmorty.data.repository.FakeCharacterRepository
import yassir.challenge.rickandmorty.domain.usecase.GetAllCharacterUseCase
import yassir.challenge.rickandmorty.presentation.character_list.CharacterViewModel
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListAction
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterListItem
import yassir.challenge.rickandmorty.presentation.character_list.state.CharacterPagingState
import java.io.IOException


class CharacterViewModelTest {


    private val getCharacters: GetAllCharacterUseCase = mockk(relaxed = true)

    lateinit var viewModel: CharacterViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CharacterViewModel(SavedStateHandle(), getCharacters)
    }


    @Test
    fun ` check initial state is correct `() {
        assert(viewModel.state.value.searchQuery.isEmpty())
        assert(viewModel.state.value.isSearching.not())
        assert(viewModel.state.value.isLoading.not())
        assert(viewModel.state.value.characterPagingState is CharacterPagingState.Loading)
    }

    @Test
    fun `check query update correctly`() {
        viewModel.onAction(CharacterListAction.OnSearchQueryChanged("query"))
        assert(viewModel.state.value.searchQuery == "query")
    }

    @Test
    fun `search toggle update isSearch and clear the test after toggle off `() {
        viewModel.onAction(CharacterListAction.OnSearchToggle)
        assert(viewModel.state.value.isSearching)
        viewModel.onAction(CharacterListAction.OnSearchQueryChanged("query"))
        assert(viewModel.state.value.searchQuery == "query")
        viewModel.onAction(CharacterListAction.OnSearchToggle)
        assert(!viewModel.state.value.isSearching)
        assert(viewModel.state.value.searchQuery == "")
    }


    @Test
    fun `emit Navigation details event when onItemClicked`() = runTest {
        viewModel.events.test {
            viewModel.onAction(CharacterListAction.OnItemClicked(1))
            assert(CharacterViewModel.UiEvent.NavigateToDetail(1) == awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emit OnErrorNetworkSettings event when OnErrorNetworkSettings`() = runTest {
        viewModel.events.test {
            viewModel.onAction(CharacterListAction.OnErrorNetworkSettings)
            assert(CharacterViewModel.UiEvent.OnErrorNetworkSettings == awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emit OnErrorRetry event when OnErrorRetry`() = runTest {
        viewModel.events.test {
            viewModel.onAction(CharacterListAction.OnErrorRetry)
            assert(CharacterViewModel.UiEvent.OnErrorRetry == awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `characterPaging emit new data when query changed `() = runTest {

        val paginationFlow = flowOf(PagingData.from(FakeCharacterRepository.getCharacters().map { it.toDomain() }))

        every { getCharacters("Morty") } returns paginationFlow

        val collectedItems = mutableListOf<PagingData<CharacterListItem>>()

        viewModel.characterPaging.test {
            viewModel.onAction(CharacterListAction.OnSearchQueryChanged("Morty"))
            collectedItems.add(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        assert(collectedItems.isNotEmpty())

    }

    @Test
    fun `characterPaging emit  data when  search empty (default) `() = runTest {
        val paginationFlow = flowOf(PagingData.from(FakeCharacterRepository.getCharacters().map { it.toDomain() }))

        every { getCharacters() } returns paginationFlow

        val collectedItems = mutableListOf<PagingData<CharacterListItem>>()


        viewModel.characterPaging.test {
            collectedItems.add(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        assert(collectedItems.isNotEmpty())
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `characterPaging test when Throw IOException`() = runTest { ->
        every { getCharacters("Morty") } throws IOException("No internet")

        viewModel.onAction(CharacterListAction.OnSearchQueryChanged("Morty"))

        advanceUntilIdle()

        val state = viewModel.state.value

        assert(state.characterPagingState is CharacterPagingState.Error)

    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `characterPaging test when Throw RuntimeException`() = runTest { ->
        every { getCharacters("Morty") } throws RuntimeException("Unexpected  Error")

        viewModel.onAction(CharacterListAction.OnSearchQueryChanged("Morty"))

        advanceUntilIdle()

        val state = viewModel.state.value

        assert(state.characterPagingState is CharacterPagingState.Error)

        val isNetworkError = (state.characterPagingState as CharacterPagingState.Error).isNetworkError

        assert(!isNetworkError)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `characterPaging handle HttpException 404  empty result `() = runTest {

        val errorResponse = Response.error<String>(404, "".toResponseBody())

        val httpException = HttpException(errorResponse)

        every { getCharacters("Yassir") } throws httpException

        viewModel.onAction(CharacterListAction.OnSearchQueryChanged("Yassir"))

        advanceUntilIdle()

        val state = viewModel.state.value

        val pagingState = state.characterPagingState

        assert(pagingState is CharacterPagingState.Empty)
    }


}