package pack.logindemo.app.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import pack.logindemo.app.R
import pack.logindemo.app.adapter.ProfileAdapter
import pack.logindemo.app.databinding.FragmentProfilePageBinding
import pack.logindemo.app.viewmodel.ProfilePageViewModel

class ProfilePageFragment : Fragment() {
    lateinit var binding : FragmentProfilePageBinding
    var viewModel : ProfilePageViewModel = ProfilePageViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_page, container, false)
        viewModel = ViewModelProvider(this).get(ProfilePageViewModel::class.java)
        initListView()
        return binding.root
    }

    fun initListView() {
        viewModel.addItemsFromJSON(context!!)
        viewModel.rewardList.observe(viewLifecycleOwner){
            binding.listRewards.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = ProfileAdapter(it)
            }
        }
    }

}