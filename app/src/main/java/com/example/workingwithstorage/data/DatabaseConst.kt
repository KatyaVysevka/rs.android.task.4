package com.example.workingwithstorage.data

const val DATABASE_NAME = "user_database"
const val TABLE_NAME = "film_table"
const val DATABASE_VERSION = 1

const val COLUMN_ID = "id"
const val COLUMN_TITLE = "title"
const val COLUMN_COUNTRY = "country"
const val COLUMN_YEAR = "year"

const val CREATE_TABLE_SQL =
    "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "$COLUMN_TITLE TEXT NOT NULL, " +
            "$COLUMN_COUNTRY TEXT NOT NULL, " +
            "$COLUMN_YEAR INTEGER NOT NULL" +
        ")"

const val DELETE_TABLE_SQL = "DROP TABLE IF EXISTS $TABLE_NAME"
