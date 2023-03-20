package pack.logindemo.app.utils

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Utils {
    fun pinMatch(context: Context,mpin: String, confirmMpin: String) : Boolean {
        if(mpin.equals(confirmMpin)) {
            return true
        } else {
            Toast.makeText(context, "MPIN does not match.", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    fun checkEmptyFields(context: Context, firstName: String, lastName: String, number: String, mpin: String, confirmMpin: String) : Boolean {
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(number)
            || TextUtils.isEmpty(mpin) || TextUtils.isEmpty(confirmMpin)) {
            Toast.makeText(context, "Please fill all credentials field", Toast.LENGTH_SHORT).show()
            return true
        } else {
            return false
        }
    }

    fun checkEmptyField(button: Button, firstName: EditText?, lastName: EditText?, phonenumber: EditText?, mPin: EditText?, confirmMpin: EditText?) {
        val mTextWatcher = object : TextWatcher {
            override fun afterTextChanged(et: Editable?) {
                val first = firstName?.text.toString().trim()
                val last = lastName?.text.toString().trim()
                val num = phonenumber?.text.toString().trim()
                val mpin = mPin?.text.toString().trim()
                val cmpin = confirmMpin?.text.toString().trim()

                button.isEnabled = first.isNotEmpty() && last.isNotEmpty() && num.isNotEmpty() && mpin.isNotEmpty() && cmpin.isNotEmpty()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        }
        firstName?.addTextChangedListener(mTextWatcher)
        lastName?.addTextChangedListener(mTextWatcher)
        phonenumber?.addTextChangedListener(mTextWatcher)
        mPin?.addTextChangedListener(mTextWatcher)
        confirmMpin?.addTextChangedListener(mTextWatcher)
    }
}