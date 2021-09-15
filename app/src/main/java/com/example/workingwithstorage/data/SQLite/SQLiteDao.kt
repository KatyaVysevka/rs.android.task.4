package com.example.workingwithstorage.data.SQLite

import android.content.ContentValues
import com.example.workingwithstorage.data.*
import com.example.workingwithstorage.model.Film
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SQLiteDao @Inject constructor(
    private val sqlLite: FilmSQLiteHelper,
    private val preferencesManager: PreferenceManager
) : DatabaseStrategy {

    override suspend fun addFilm(film: Film) {
        val db = sqlLite.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, film.title)
            put(COLUMN_COUNTRY, film.country)
            put(COLUMN_YEAR, film.year)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
        preferencesManager.updateTrigger("insert")
    }

    override suspend fun updateFilm(film: Film) {
        val db = sqlLite.writableDatabase
        db?.delete(TABLE_NAME, "id = ?", arrayOf(film.id.toString()))
        db?.close()
        preferencesManager.updateTrigger("update")
    }

    override suspend fun deleteFilm(film: Film) {
        val db = sqlLite.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, film.title)
            put(COLUMN_COUNTRY, film.country)
            put(COLUMN_YEAR, film.year)
        }
        db.update(TABLE_NAME, values, "id = ?", arrayOf(film.id.toString()))
        db.close()
        preferencesManager.updateTrigger("delete")
    }

    override fun readAllData(): Flow<List<Film>> {
        val listOfTopics = mutableListOf<Film>()
        val db = sqlLite.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        with(cursor) {
            while (moveToNext()) {
                listOfTopics.add(
                    Film(
                        getInt(0),
                        getString(1),
                        getString(2),
                        getInt(3)

                    )
                )
            }
        }
        cursor.close()
        db.close()
        preferencesManager.trigger

        // [nit]
        // Этот Flow отправляет только одно значение и не обновляется динамически
        // при добавлении. В таких случаях (когда отправляется только одно значение и один раз)
        // возвращают сразу `List<Film>` вместо `Flow<List<Film>>`.
        // Чтобы сделать динамическим, надо посмотреть способы получения нотификации, что что-то
        // изменилось в базе, или самим триггерить нотификацию при вызове методов
        // addFilm/updateFilm/deleteFilm.
        return flowOf(listOfTopics)
    }


    override fun sortedByTitle(): Flow<List<Film>> {
        val listOfTopics = mutableListOf<Film>()
        val db = sqlLite.readableDatabase
        val cursor = db.query(
            TABLE_NAME, null,
            null, null, null, null,
            "$COLUMN_TITLE ASC"
        )

        with(cursor) {
            while (moveToNext()) {
                listOfTopics.add(
                    Film(
                        getInt(0),
                        getString(1),
                        getString(2),
                        getInt(3)

                    )
                )
            }
        }
        cursor.close()
        db.close()
        preferencesManager.trigger
        return flowOf(listOfTopics)
    }

    override fun sortedByCountry(): Flow<List<Film>> {
        val listOfTopics = mutableListOf<Film>()
        val db = sqlLite.readableDatabase
        val cursor = db.query(
            TABLE_NAME, null,
            null, null, null, null,
            "$COLUMN_COUNTRY ASC"
        )

        with(cursor) {
            while (moveToNext()) {
                listOfTopics.add(
                    Film(
                        getInt(0),
                        getString(1),
                        getString(2),
                        getInt(3)

                    )
                )
            }
        }
        cursor.close()
        db.close()
        preferencesManager.trigger
        return flowOf(listOfTopics)
    }

    override fun sortedByYear(): Flow<List<Film>> {
        val listOfTopics = mutableListOf<Film>()
        val db = sqlLite.readableDatabase
        val cursor = db.query(
            TABLE_NAME, null,
            null, null, null, null,
            "$COLUMN_YEAR ASC"
        )

        with(cursor) {
            while (moveToNext()) {
                listOfTopics.add(
                    Film(
                        getInt(0),
                        getString(1),
                        getString(2),
                        getInt(3)

                    )
                )
            }
        }
        cursor.close()
        db.close()
        preferencesManager.trigger
        return flowOf(listOfTopics)
    }
}