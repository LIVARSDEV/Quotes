package com.example.quotes

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.quotes.recyclerView.QuoteModelClass

class SqliteDatabase(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{

        private const val DATABASE_NAME = "QuotesDatabase"
        private const val DATABASE_VERSION = 1

        private const val QUOTES_TABLE = "QuotesTable"
        private const val ID_COLUMN = "QuoteId"
        private const val QUOTES_COLUMN = "Quotes"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val quotesQuery = "CREATE TABLE IF NOT EXISTS $QUOTES_TABLE ($ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, $QUOTES_COLUMN TEXT )"
        db?.execSQL(quotesQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun addQuoteToDb(quote: String){
        val sqliteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(QUOTES_COLUMN, quote)
        sqliteDatabase.insert(QUOTES_TABLE, null, contentValues)

    }

    fun retrieveAllQuotes(): MutableList<QuoteModelClass> {
        val sqliteDatabase = this.readableDatabase
        val cursor = sqliteDatabase.rawQuery("SELECT * FROM $QUOTES_TABLE", null)
        val quotesList = mutableListOf<QuoteModelClass>()
        while (cursor.moveToNext()) {
            val quoteInput = cursor.getString(cursor.getColumnIndexOrThrow(QUOTES_COLUMN))
            val quote = QuoteModelClass(quoteInput)

            quotesList.add(quote)

        }
        cursor.close()
    return quotesList

    }

    fun updateQuote(whichQuote: String, newString: String) {
        val sqliteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(QUOTES_COLUMN, newString)
        sqliteDatabase.update(QUOTES_TABLE, contentValues, "Quotes=?", arrayOf(whichQuote))

    }

    fun deleteQuote(quote: String){
        val sqliteDatabase = this.writableDatabase
        sqliteDatabase.delete(QUOTES_TABLE, "Quotes=?", arrayOf(quote))

    }
}
