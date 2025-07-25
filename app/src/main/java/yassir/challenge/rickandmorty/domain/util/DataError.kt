package yassir.challenge.rickandmorty.domain.util

sealed interface DataError : Error {


    enum class Network : DataError {
        NETWORK_UNAVAILABLE,
        UNAVAILABLE,
        SERVER_ERROR,
        Unknown
    }
}