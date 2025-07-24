package yassir.challenge.rickandmorty.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import yassir.challenge.rickandmorty.data.remote.CharacterApi
import yassir.challenge.rickandmorty.data.repository.CharacterRepositoryImpl
import yassir.challenge.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule  {

    private const val BASE_URL ="https://rickandmortyapi.com/api/"


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val contentType = MediaType.get("application/json")
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideCharacterApi(retrofit: Retrofit): CharacterApi {
        return retrofit.create(CharacterApi::class.java)
    }


    @Provides
    @Singleton
    fun provideCharacterRepository(
        api: CharacterApi
    ): CharacterRepository = CharacterRepositoryImpl(api)


}