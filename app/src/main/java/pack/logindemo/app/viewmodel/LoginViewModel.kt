package pack.logindemo.app.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pack.logindemo.app.sqlite.DBHelper

class LoginViewModel : ViewModel() {
    val userData : MutableLiveData<Boolean?> = MutableLiveData()

    val exe = CoroutineExceptionHandler { _, _ ->
        userData.postValue(null)
    }

    fun userLoginData(db: DBHelper ,phoneNumber: String, mPin: String) {
        viewModelScope.launch (exe + Dispatchers.IO){
            try {
                val logData = db.checkCredentials(phoneNumber, mPin)
                userData.postValue(logData)
            } catch (exception: Exception) {
                userData.postValue(null)
            }
        }
    }
}