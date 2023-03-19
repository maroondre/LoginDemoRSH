package pack.logindemo.app.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import pack.logindemo.app.model.UserDTO

class DBHelper(context: Context) : SQLiteOpenHelper(context, "userDataProfile", null, 1) {

    private val CREATE_USER_TABLE = ("CREATE TABLE " + userTableName + "("
            + userId + " INTEGER PRIMARY KEY AUTOINCREMENT," + userName + " TEXT," + userLastName + " TEXT,"
            + userPhoneNumber + " TEXT," + userMPIN + " TEXT," + userConfMPIN + " TEXT" + ")")

    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $userTableName"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_USER_TABLE)
    }

    fun insertUserData(userDTO: UserDTO): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(userName, userDTO.firstName)
        contentValues.put(userLastName, userDTO.lastName)
        contentValues.put(userPhoneNumber, userDTO.mobileNumber)
        contentValues.put(userMPIN, userDTO.mPin)
        contentValues.put(userConfMPIN, userDTO.confirmMpin)

        val result = db.insert(userTableName, null, contentValues)
        return if (result == (-1).toLong()) {
            false
        } else {
            db.close()
            true
        }
    }

    fun checkCredentials(userNumber: String, userMPIN: String): Boolean {
        val db = this.writableDatabase
        val selectQuery =
            "select * from $userTableName where userPhoneNumber = '$userNumber' and userMPIN = '$userMPIN'"

        val cursor = db.rawQuery(selectQuery, null)
        return if (cursor.count > 0) {
            cursor.close()
            db.close()
            true
        } else {
            cursor.close()
            db.close()
            false
        }

    }
    fun checkNumberExists(userNumber: String): Boolean {
        val db = this.readableDatabase
        val selectQuery =
            "select * from $userTableName where userPhoneNumber = '$userNumber'"

        val cursor = db.rawQuery(selectQuery, null)
        cursor.moveToFirst();

        // cursor.getInt(0) is 1 if column with value exists
        return if (cursor.getInt(0) == 1) {
            cursor.close()
            db.close()
            true
        } else {
            cursor.close()
            db.close()
            false
        }

    }

    fun viewData(userNumber: String) : Cursor {
        val db = this.readableDatabase
        val selectQuery = "select * from $userTableName where $userPhoneNumber = '$userNumber'"

        val cursor = db.rawQuery(selectQuery, null)
        cursor?.moveToFirst()
        return cursor

    }

    companion object {
        private val userTableName = "user"

        private val userId = "userId"
        private val userName = "userName"
        private val userLastName = "userLastName"
        private val userPhoneNumber = "userPhoneNumber"
        private val userMPIN = "userMPIN"
        private val userConfMPIN = "userConfirmMPIN"
    }
}