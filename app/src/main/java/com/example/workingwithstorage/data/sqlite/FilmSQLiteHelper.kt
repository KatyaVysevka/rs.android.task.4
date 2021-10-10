package com.example.workingwithstorage.data.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.workingwithstorage.data.CREATE_TABLE_SQL
import com.example.workingwithstorage.data.DATABASE_NAME
import com.example.workingwithstorage.data.DATABASE_VERSION
import com.example.workingwithstorage.data.DELETE_TABLE_SQL
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FilmSQLiteHelper @Inject constructor(@ApplicationContext context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    /**
     * Оставим здесь только sanity check, что в базе есть нужная таблица.
     * Иницилизацию дефолтом оставим на откуп [com.example.workingwithstorage.data.room.FilmDatabase].
     */
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_SQL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DELETE_TABLE_SQL)
        onCreate(db)
    }
}