package yassir.challenge.rickandmorty.domain.util


interface Error




sealed interface Result<out D,out E : Error> {


    data class Success<out D,out E : Error>(val data : D) : Result<D,E>

    data class Failure<out D,out E : Error>(val error: E) : Result<D,E>


    fun handleResult(onSuccess: (D) -> Unit, onFailure: (E) -> Unit) {
        when (this) {
            is Success -> onSuccess(data)
            is Failure -> onFailure(error)
        }
    }
}