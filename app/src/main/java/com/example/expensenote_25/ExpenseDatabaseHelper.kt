package com.example.expensenote_25

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ExpenseDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "expense_note.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "expenses"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_TYPE = "type"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_DATE = "date"

        const val TYPE_INCOME = "INCOME"
        const val TYPE_EXPENSE = "EXPENSE"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT NOT NULL,
                $COLUMN_AMOUNT REAL NOT NULL,
                $COLUMN_TYPE TEXT NOT NULL,
                $COLUMN_CATEGORY TEXT NOT NULL,
                $COLUMN_DATE TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertExpense(item: ExpenseItem): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, item.title)
            put(COLUMN_AMOUNT, item.amount)
            put(COLUMN_TYPE, item.type)
            put(COLUMN_CATEGORY, item.category)
            put(COLUMN_DATE, item.date)
        }
        val id = db.insert(TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun getAllExpenses(): List<ExpenseItem> {
        val list = mutableListOf<ExpenseItem>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_ID DESC", null)

        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID)
            val titleIndex = cursor.getColumnIndexOrThrow(COLUMN_TITLE)
            val amountIndex = cursor.getColumnIndexOrThrow(COLUMN_AMOUNT)
            val typeIndex = cursor.getColumnIndexOrThrow(COLUMN_TYPE)
            val categoryIndex = cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)
            val dateIndex = cursor.getColumnIndexOrThrow(COLUMN_DATE)

            do {
                val item = ExpenseItem(
                    id = cursor.getLong(idIndex),
                    title = cursor.getString(titleIndex),
                    amount = cursor.getDouble(amountIndex),
                    type = cursor.getString(typeIndex),
                    category = cursor.getString(categoryIndex),
                    date = cursor.getString(dateIndex)
                )
                list.add(item)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }

    fun deleteExpense(id: Long): Boolean {
        val db = writableDatabase
        val rowsDeleted = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return rowsDeleted > 0
    }

    fun getTotalIncome(): Double {
        var total = 0.0
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT SUM($COLUMN_AMOUNT) FROM $TABLE_NAME WHERE $COLUMN_TYPE = ?",
            arrayOf(TYPE_INCOME)
        )
        if (cursor.moveToFirst() && !cursor.isNull(0)) {
            total = cursor.getDouble(0)
        }
        cursor.close()
        db.close()
        return total
    }

    fun getTotalExpense(): Double {
        var total = 0.0
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT SUM($COLUMN_AMOUNT) FROM $TABLE_NAME WHERE $COLUMN_TYPE = ?",
            arrayOf(TYPE_EXPENSE)
        )
        if (cursor.moveToFirst() && !cursor.isNull(0)) {
            total = cursor.getDouble(0)
        }
        cursor.close()
        db.close()
        return total
    }
}
