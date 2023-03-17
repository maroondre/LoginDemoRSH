package pack.logindemo.app.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import pack.logindemo.app.R
import pack.logindemo.app.utils.RewardsList
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import org.json.JSONException
import org.json.JSONArray
import pack.logindemo.app.adapter.ProfileAdapter
import pack.logindemo.app.databinding.FragmentProfilePageBinding
import pack.logindemo.app.model.RewardsDTO

class ProfilePageFragment : Fragment() {
    var rewardList = RewardsList()
    var list: ArrayList<RewardsDTO> = ArrayList()
    lateinit var binding : FragmentProfilePageBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_page, container, false)
        initListView()
        return binding.root
    }

    fun initListView() {
        addItemsFromJSON()
        binding.listRewards.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ProfileAdapter(list)
        }
    }

    private fun addItemsFromJSON() {
        try {
            val jsonData = readJSONDataFromFile()
            val jsonArray = JSONArray(jsonData)
            for (i in 0 until jsonArray.length()) {
                val itemObj = jsonArray.getJSONObject(i)
                val desc = itemObj.getString("description")
                val image = itemObj.getString("image")
                val rewards = RewardsDTO(null, desc, image)
                list.add(rewards)
            }
        } catch (e: JSONException) {
            Log.d("Fetch failed", "Something went wrong!", e)
        }
    }

    @Throws(IOException::class)
    private fun readJSONDataFromFile(): String {

        var inputStream: InputStream? = null
        val builder = StringBuilder()
        try {
            var jsonString: String?
            inputStream = resources.openRawResource(R.raw.rewards)
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