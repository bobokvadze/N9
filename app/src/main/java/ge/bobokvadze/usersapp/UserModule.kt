package ge.bobokvadze.usersapp

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    @Singleton
    fun provideUsersRepository(): UserRepository = UserRepository.Base()

    @Provides
    fun provideCommunication(): Communication = Communication.Base()
}
