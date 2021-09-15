package com.example.workingwithstorage.data

const val DATABASE_NAME = "FILM_DATABASE"
const val TABLE_NAME = "film_table"
const val DATABASE_VERSION = 1

const val COLUMN_ID = "id"
const val COLUMN_TITLE = "title"
const val COLUMN_COUNTRY = "country"
const val COLUMN_YEAR = "year"

const val CREATE_TABLE_SQL =
    "CREATE TABLE IF NOT EXISTS $TABLE_NAME (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "$COLUMN_TITLE TEXT, " +
            "$COLUMN_COUNTRY TEXT, " +
            "$COLUMN_YEAR INTEGER)"

const val DELETE_TABLE_SQL = "DROP TABLE IF EXISTS $TABLE_NAME"

enum class TypeDB {
    ROOM, SQL_LITE
}