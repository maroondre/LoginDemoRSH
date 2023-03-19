package pack.logindemo.app.view

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import pack.logindemo.app.R
import pack.logindemo.app.adapter.ProfileAdapter
import pack.logindemo.app.databinding.FragmentProfilePageBinding
import pack.logindemo.app.sqlite.DBHelper
import pack.logindemo.app.viewmodel.ProfilePageViewModel

const val GET_USER_NUMBER = "get_user_phone_number"

class ProfilePageFragment : Fragment() {
    lateinit var binding: FragmentProfilePageBinding
    var viewModel: ProfilePageViewModel = ProfilePageViewModel()
    lateinit var db: DBHelper
    lateinit var cursor: Cursor
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile_page, container, false)
        viewModel = ViewModelProvider(this).get(ProfilePageViewModel::class.java)
        initListView()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    fun initListView() {
        val getUserNumber = arguments?.getString(GET_USER_NUMBER)
        db = DBHelper(context!!)
        cursor = db.viewData(getUserNumber.toString())
        cursor.moveToFirst()
        binding.userName.text = "${cursor.getString(1)} + ${cursor.getString(2)}"

        viewModel.addItemsFromJSON(context!!)
        viewModel.rewardList.observe(viewLifecycleOwner) {
            binding.listRewards.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = ProfileAdapter(it)
            }
        }
    }

}