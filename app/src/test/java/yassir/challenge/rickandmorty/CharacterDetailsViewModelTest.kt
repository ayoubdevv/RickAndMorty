package yassir.challenge.rickandmorty

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import yassir.challenge.rickandmorty.data.remote.toDomain
import yassir.challenge.rickandmorty.data.repository.FakeCharacterRepository
import yassir.challenge.rickandmorty.domain.usecase.GetCharacterDetailsUseCase
import yassir.challenge.rickandmorty.domain.util.DataError
import yassir.challenge.rickandmorty.domain.util.Result
import yassir.challenge.rickandmorty.presentation.Destination
import yassir.challenge.rickandmorty.presentation.charactar_details.CharacterDetailsViewModel
import yassir.challenge.rickandmorty.presentation.mapper.toDetails

class CharacterDetailsViewModelTest {


    lateinit var viewModel: CharacterDetailsViewModel

    private val detailsUseCase = mockk<GetCharacterDetailsUseCase>()

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()

    val savedStateHandle = SavedStateHandle(mapOf("characterId" to 5))

    private val fakeList = FakeCharacterRepository.getCharacters().map { it.toDomain() }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic("androidx.navigation.SavedStateHandleKt")
        every { savedStateHandle.toRoute<Destination.CharacterDetails>() } returns Destination.CharacterDetails(5)
        viewModel = CharacterDetailsViewModel(savedStateHandle, detailsUseCase)
    }


    @Test
    fun `check initial state is correct`() = runTest {
        viewModel.state.test {
            val state = awaitItem()
            assert(!state.isLoading)
            assert(state.detailItem == null)
            assert(!state.isNetworkError)
            assert(state.errorMessage == null)
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `loadCharacter test and loading state`() = runTest {

        coEvery { detailsUseCase(5) } returns Result.Success(fakeList.first())

        viewModel.loadCharacterDetails(5)
        viewModel.state.test {

            val loading = awaitItem()

            assert(loading.isLoading)

            advanceTimeBy(300)
            val loadedState = awaitItem()

            assert(loadedState.detailItem == fakeList.first().toDetails())

            assert(!loadedState.isLoading)

            cancelAndIgnoreRemainingEvents()
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test Failure State UnknowError`() = runTest {
        coEvery { detailsUseCase(5) } returns Result.Failure(DataError.Network.Unknown)

        viewModel.loadCharacterDetails(5)
        viewModel.state.test {
            val loading = awaitItem()
            assert(loading.isLoading)
            advanceTimeBy(300)
            val loadedState = awaitItem()
            assert(!loadedState.isLoading)
            assert(!loadedState.isNetworkError)
            assert(loadedState.errorMessage == R.string.error_unknown)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test Failure State SERVER_ERROR`() = runTest {
        coEvery { detailsUseCase(5) } returns Result.Failure(DataError.Network.SERVER_ERROR)

        viewModel.loadCharacterDetails(5)
        viewModel.state.test {
            val loading = awaitItem()
            print(loading)
            assert(loading.isLoading)
            advanceTimeBy(300)
            val loadedState = awaitItem()
            assert(!loadedState.isLoading)
            assert(!loadedState.isNetworkError)
            assert(loadedState.errorMessage == R.string.error_server_error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test Failure State UNAVAILABLE (User not Found)`() = runTest {
        coEvery { detailsUseCase(5) } returns Result.Failure(DataError.Network.UNAVAILABLE)

        viewModel.loadCharacterDetails(5)
        viewModel.state.test {
            val loading = awaitItem()
            assert(loading.isLoading)
            advanceTimeBy(300)
            val loadedState = awaitItem()
            assert(!loadedState.isLoading)
            assert(!loadedState.isNetworkError)
            assert(loadedState.errorMessage == R.string.no_results_title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test Failure State NETWORK_UNAVAILABLE (User not Found)`() = runTest {
        coEvery { detailsUseCase(5) } returns Result.Failure(DataError.Network.NETWORK_UNAVAILABLE)

        viewModel.loadCharacterDetails(5)
        viewModel.state.test {
            val loading = awaitItem()
            assert(loading.isLoading)
            advanceTimeBy(300)
            val loadedState = awaitItem()
            assert(!loadedState.isLoading)
            assert(loadedState.isNetworkError)
            assert(loadedState.errorMessage == R.string.error_no_internet)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Retry test and loading state`() = runTest {

        coEvery { detailsUseCase(5) } returns Result.Failure(DataError.Network.NETWORK_UNAVAILABLE)

        viewModel.loadCharacterDetails(5)
        viewModel.state.test {

            val loading = awaitItem()

            assert(loading.isLoading)

            advanceTimeBy(300)
            val loadedState = awaitItem()

            assert(loadedState.isNetworkError)

            assert(!loadedState.isLoading)

            coEvery { detailsUseCase(5) } returns Result.Success(fakeList.first())

            viewModel.onRetry()

            val retryLoading = awaitItem()

            assert(retryLoading.isLoading)

            advanceTimeBy(300)

            val retryLoaded = awaitItem()

            assert(retryLoaded.detailItem == fakeList.first().toDetails())

            cancelAndIgnoreRemainingEvents()
        }
    }


}