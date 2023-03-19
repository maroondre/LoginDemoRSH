package pack.logindemo.app.utils

import android.content.Context
import android.widget.Toast

class Utils {
    fun pinMatch(context: Context,mpin: String, confirmMpin: String) : Boolean {
        if(mpin.equals(confirmMpin)) {
            return true
        } else {
            Toast.makeText(context, "MPIN does not match.", Toast.LENGTH_LONG).show()
            return false
        }
    }
}