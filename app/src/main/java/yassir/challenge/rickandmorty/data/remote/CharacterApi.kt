package yassir.challenge.rickandmorty.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import yassir.challenge.rickandmorty.data.dto.CharacterDto
import yassir.challenge.rickandmorty.data.dto.CharacterResponseDto

interface CharacterApi {

    @GET("character")
    suspend fun getCharacterList(@Query("page") page: Int): CharacterResponseDto

    @GET("character/{characterId}")
    suspend fun getCharacterDetails(@Path("characterId") characterId: Int): CharacterDto
}