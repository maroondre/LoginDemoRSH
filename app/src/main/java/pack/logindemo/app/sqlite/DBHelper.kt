package pack.logindemo.app.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import pack.logindemo.app.model.UserDTO

class DBHelper(context : Context):SQLiteOpenHelper(context, "userDataProfile", null, 1) {

    private val CREATE_USER_TABLE = ("CREATE TABLE " + userTableName + "("
            + userID + " INTEGER PRIMARY KEY AUTOINCREMENT," + userName + " TEXT,"
            + userPhoneNumber + " TEXT," + userMPIN + " TEXT" + ")")

    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $userTableName"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_USER_TABLE)
    }

    fun insertUserData(userDTO: UserDTO) {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(userName,userDTO.userName)
        contentValues.put(userMPIN, userDTO.mpin)

        db.insert(userTableName, null, contentValues)
        db.close()
    }

    fun checkCredentials(userDTO: UserDTO) : Boolean{
        val db = this.writableDatabase
        val selectQuery = "select * from $userTableName where userName = ${userDTO.userName} and userPassword = ${userDTO.mpin}"

        val cursor = db.rawQuery(selectQuery,null)
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

    companion object {
        private val userTableName = "user"

        private val userID = "userId"
        private val userName = "userName"
        private val userPhoneNumber = "userPhoneNumber"
        private val userMPIN = "userPassword"
    }
}