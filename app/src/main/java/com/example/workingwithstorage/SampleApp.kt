package com.example.workingwithstorage

import android.app.Application
import com.example.workingwithstorage.data.room.FilmDatabase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class SampleApp : Application() {

    /**
     * Хак, чтобы на старте приложения наша база сразу проинитилась дефолтными
     * значениями.
     */
    @Inject
    lateinit var filmDatabase: FilmDatabase
}