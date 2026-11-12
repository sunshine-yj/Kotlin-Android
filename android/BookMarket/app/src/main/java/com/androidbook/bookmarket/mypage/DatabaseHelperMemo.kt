package com.androidbook.bookmarket.mypage


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper



class DatabaseHelperMemo(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
){
    companion object {
        const val DATABASE_NAME = "Memo.db"
        const val DATABASE_VERSION = 1;
        const val TABLE_NAME = "MemoTABLE";
        const val KEY_ID = "id"
        const val KEY_DATE = "date"
        const val KEY_TITLE = "title"
        const val KEY_CONTENT = "content"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val query =  ("CREATE TABLE " + TABLE_NAME + "("
            + KEY_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_DATE + " TEXT,"
            + KEY_TITLE + " TEXT,"
            + KEY_CONTENT + " TEXT " + ")")
        p0?.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val query = "DROP TABLE IF EXISTS " +TABLE_NAME
        p0?.execSQL(query)
        onCreate(p0)
    }

    fun insertData(date:String, title: String, content: String) {
        val db  = this.writableDatabase
        val cv = ContentValues()
        cv.put(KEY_DATE, date)
        cv.put(KEY_TITLE, title)
        cv.put(KEY_CONTENT, content)

        val result = db.insert(TABLE_NAME, null, cv)
    }

    fun readData() : ArrayList<HashMap<String, String>>{
        val db  = this.writableDatabase
        val memoList: ArrayList<HashMap<String, String>> = ArrayList()
        val query =  "SELECT id, date, title, content FROM "+ TABLE_NAME;
        val cursor = db.rawQuery(query, null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val user: HashMap<String, String> = HashMap()
                user.put("id", cursor.getInt(0).toString())
                user.put("date", cursor.getString(1))
                user.put("title", cursor.getString(2))
                user.put("content", cursor.getString(3))

                memoList.add(user)
            }
        }
        db.close()

        return  memoList;
    }

    fun updateData(date: String, title: String, content: String, id: Int) : Int{
        val db = this.writableDatabase
        val cVals = ContentValues()
        cVals.put(KEY_DATE, date)
        cVals.put(KEY_TITLE, title)
        cVals.put(KEY_CONTENT, content)

        val count =
            db.update(TABLE_NAME, cVals, "$KEY_ID = ?", arrayOf(java.lang.String.valueOf(id)))
        return count
    }

    fun deleteData(userid: Int){
        val db : SQLiteDatabase = this.writableDatabase
        db.delete(TABLE_NAME, "$KEY_ID = ?", arrayOf(java.lang.String.valueOf(userid)));
    }

    fun deleteAllData(){
        val db : SQLiteDatabase = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
    }

    fun searchReadData(content: String) : ArrayList<HashMap<String, String>>{
        val db  = this.writableDatabase
        val memoList: ArrayList<HashMap<String, String>> = ArrayList()
        val query =  "SELECT id, date, title, content FROM "+ TABLE_NAME;
        val cursor = db.query(TABLE_NAME,
            null,
            "$KEY_CONTENT LIKE ?",
            arrayOf(java.lang.String.valueOf("%"+content+"%")),
            null,
            null,
            null );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val user: HashMap<String, String> = HashMap()
                user.put("id", cursor.getString(0));
                user.put("date", cursor.getString(1))
                user.put("title", cursor.getString(2))
                user.put("content", cursor.getString(3))

                memoList.add(user);
            }
        }
        cursor.close()

        return  memoList;
    }

    fun searchReadData2(id: Int) : HashMap<String, String>{
        val db  = this.writableDatabase
        val user: HashMap<String, String> = HashMap()
        val query =  "SELECT id, date, title, content FROM "+ TABLE_NAME;
        val cursor = db.query(TABLE_NAME, null, "$KEY_ID = ?", arrayOf(java.lang.String.valueOf(id)), null, null, null );

        if (cursor != null) {
            user.put("id", cursor.getString(cursor.getColumnIndex(KEY_ID)));
            user.put("date", cursor.getString(cursor.getColumnIndex(KEY_DATE)))
            user.put("title", cursor.getString(cursor.getColumnIndex(KEY_TITLE)))
            user.put("content", cursor.getString(cursor.getColumnIndex(KEY_CONTENT)))

        }
        cursor.close()

        return  user;
    }

}

