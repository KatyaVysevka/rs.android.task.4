package com.example.workingwithstorage.data.SQLite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.workingwithstorage.data.*


class FilmSQLiteHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_SQL)
        db.execSQL(
            "INSERT INTO $TABLE_NAME ($COLUMN_TITLE) VALUES ('Fight Club') " +
                    "($COLUMN_COUNTRY) VALUES ('USA')($COLUMN_YEAR) VALUES (1999);"
        )
        db.execSQL(
            "INSERT INTO $TABLE_NAME ($COLUMN_TITLE) VALUES ('The Dark Knight') " +
                    "($COLUMN_COUNTRY) VALUES ('USA')($COLUMN_YEAR) VALUES (2008);"
        )
        db.execSQL(
            "INSERT INTO $TABLE_NAME ($COLUMN_TITLE) VALUES ('The Lion King') " +
                    "($COLUMN_COUNTRY) VALUES ('USA')($COLUMN_YEAR) VALUES ('1994');"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DELETE_TABLE_SQL)
        onCreate(db)
    }
}