package cnns.com.example.kotlintestapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASENAME = "DB_NAME"
val TABLENAME = "TABLE_NAME"
val COL_TITLE = "title"
val COL_DESC = "description"
val COL_REGION = "region"
val COL_ID = "id"

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,
    1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLENAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_TITLE + " VARCHAR(256)," + COL_DESC + " INTEGER, " + COL_REGION + " INTEGER)"

        db?.execSQL(createTable)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //onCreate(db);
    }
    fun insertData(item: DepartementObject) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_TITLE, item.text1)
        contentValues.put(COL_DESC, item.text2)
        contentValues.put(COL_REGION, item.text3)

        val result = database.insert(TABLENAME, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Echec d'ajout", Toast.LENGTH_SHORT).show()
        }
        else {
//            Toast.makeText(context, "Bien ajouté dans la BD", Toast.LENGTH_SHORT).show()
        }
    }

    fun readData(limitMax: Int) : MutableList<DepartementObject> {
        val list: MutableList<DepartementObject> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME ORDER BY id DESC LIMIT $limitMax"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val item = DepartementObject("", "", 0, "")
                item.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                item.text1 = result.getString(result.getColumnIndex(COL_TITLE))
                item.text2 = result.getString(result.getColumnIndex(COL_DESC))
                item.text3 = result.getString(result.getColumnIndex(COL_REGION))

                list.add(item)
            }
            while (result.moveToNext())
        }
        return list
    }
}