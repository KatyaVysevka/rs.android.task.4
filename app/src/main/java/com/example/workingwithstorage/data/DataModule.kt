package com.example.workingwithstorage.data

import android.app.Application
import com.example.workingwithstorage.ApplicationScope
import com.example.workingwithstorage.data.room.FilmDao
import com.example.workingwithstorage.data.room.FilmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideFilmDatabase(application: Application,
                            @ApplicationScope scope: CoroutineScope): FilmDatabase {
        return FilmDatabase.getInstance(application, scope)
    }

    @Singleton
    @Provides
    fun provideFilmDao(database: FilmDatabase): FilmDao {
        return database.filmDao()
    }
}