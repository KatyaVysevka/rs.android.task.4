package com.example.workingwithstorage.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.workingwithstorage.data.SQLite.SQLiteDao
import com.example.workingwithstorage.model.Film
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@Database(entities = [Film::class], version = 1, exportSchema = false)
abstract class FilmDatabase : RoomDatabase() {

    abstract fun filmDao(): FilmDao

    companion object {
        @Volatile
        private var INSTANCE: FilmDatabase? = null

        @InternalCoroutinesApi
        fun getDatabase(context: Context, scope: CoroutineScope): FilmDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            kotlinx.coroutines.internal.synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FilmDatabase::class.java,
                    "user_database"
                ).fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    @InternalCoroutinesApi
    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        /**
         * Override the onCreate method to populate the database.
         */
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // If you want to keep the data through app restarts,
            // comment out the following line.
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
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


}
