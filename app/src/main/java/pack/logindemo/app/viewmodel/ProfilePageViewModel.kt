package pack.logindemo.app.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import pack.logindemo.app.R
import pack.logindemo.app.model.RewardsDTO
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder

class ProfilePageViewModel() : ViewModel() {
    var list: ArrayList<RewardsDTO> = ArrayList()
    var rewardList: MutableLiveData<ArrayList<RewardsDTO>> = MutableLiveData()

    val exe = CoroutineExceptionHandler { _, _ ->
        rewardList.postValue(null)
    }

    fun addItemsFromJSON(context: Context) {
        viewModelScope.launch(exe + Dispatchers.IO)
        {
            try {
                val jsonData = readJSONDataFromFile(context)
                val jsonArray = JSONArray(jsonData)
                for (i in 0 until jsonArray.length()) {
                    val itemObj = jsonArray.getJSONObject(i)
                    val desc = itemObj.getString("description")
                    val image = itemObj.getString("image")
                    val rewards = RewardsDTO(null, desc, image)
                    list.add(rewards)
                    rewardList.postValue(list)
                }
            } catch (e: JSONException) {
                Log.d("Fetch failed", "Something went wrong!", e)
            }
        }
    }

    @Throws(IOException::class)
    private fun readJSONDataFromFile(context: Context): String {

        var inputStream: InputStream? = null
        val builder = StringBuilder()
        try {
            var jsonString: String?
            inputStream = context.resources.openRawResource(R.raw.rewards)
            val bufferedReader = BufferedReader(
                InputStreamReader(inputStream, "UTF-8")
            )
            while (bufferedReader.readLine().also { jsonString = it } != null) {
                builder.append(jsonString)
            }
        } finally {
            inputStream?.close()
        }
        return String(builder)
    }
}