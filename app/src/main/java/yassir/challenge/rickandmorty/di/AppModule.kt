package yassir.challenge.rickandmorty.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import yassir.challenge.rickandmorty.data.remote.CharacterApi
import yassir.challenge.rickandmorty.data.repository.CharacterRepositoryImpl
import yassir.challenge.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule  {



    @Provides
    @Singleton
    fun provideCharacterRepository(
      //  api: CharacterApi
    ): CharacterRepository = CharacterRepositoryImpl()


}