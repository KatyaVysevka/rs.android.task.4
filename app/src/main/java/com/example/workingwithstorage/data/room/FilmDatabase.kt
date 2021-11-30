package com.example.workingwithstorage.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.workingwithstorage.data.DATABASE_NAME
import com.example.workingwithstorage.data.DATABASE_VERSION
import com.example.workingwithstorage.model.Film
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Film::class], version = DATABASE_VERSION, exportSchema = false)
abstract class FilmDatabase : RoomDatabase() {

    abstract fun filmDao(): FilmDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        /**
         * Override the onCreate method to populate the database.
         */
        override fun onCreate(db: SupportSQLiteDatabase) {
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.filmDao())
                }
            }
        }

        suspend fun populateDatabase(filmDao: FilmDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.

            var film = Film(0, "Parasite", "South Korea", 2019)
            filmDao.addFilm(film)
            film = Film(0, "1+1", "France", 2011)
            filmDao.addFilm(film)
            film = Film(0, "Deadpool", "USA", 2016)
            filmDao.addFilm(film)
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: FilmDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): FilmDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context, scope).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context, scope: CoroutineScope): FilmDatabase {
            return Room.databaseBuilder(context.applicationContext,
                FilmDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .addCallback(WordDatabaseCallback(scope))
                .build()
        }
    }
}
