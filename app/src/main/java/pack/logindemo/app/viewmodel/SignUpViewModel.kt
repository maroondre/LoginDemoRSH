package pack.logindemo.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pack.logindemo.app.model.UserDTO
import pack.logindemo.app.sqlite.DBHelper

class SignUpViewModel: ViewModel() {
    val registeredSuccessfully : MutableLiveData<Boolean?> = MutableLiveData()

    val exe = CoroutineExceptionHandler { _, _ ->
        registeredSuccessfully.postValue(null)
    }

    fun registerUser(dbHelper: DBHelper, userDTO: UserDTO){
        viewModelScope.launch(exe + Dispatchers.IO) {
            try {
                val checkNumber = dbHelper.checkNumberExists(userDTO.mobileNumber)
                val saveData = dbHelper.insertUserData(userDTO)
                if(saveData){
                    registeredSuccessfully.postValue(saveData)
                } else {
                    registeredSuccessfully.postValue(false)
                }
            } catch (exception: Exception) {
                registeredSuccessfully.postValue(null)
            }
        }

    }
}