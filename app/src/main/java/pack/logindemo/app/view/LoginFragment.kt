package pack.logindemo.app.view

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import pack.logindemo.app.R
import pack.logindemo.app.databinding.FragmentLoginBinding
import pack.logindemo.app.sqlite.DBHelper
import pack.logindemo.app.utils.Utils
import pack.logindemo.app.viewmodel.LoginViewModel
import pack.logindemo.app.viewmodel.ProfilePageViewModel

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    lateinit var db: DBHelper
    lateinit var utils: Utils
    var viewModel: LoginViewModel = LoginViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        db = DBHelper(context!!)
        utils = Utils()
        utils.checkEmptyField(
            binding.login,
            null,
            null,
            binding.inputMobileNumber,
            binding.inputMPIN,
            null
        )
        binding.login.setOnClickListener { loginUser() }
        binding.back.setOnClickListener { parentFragmentManager.popBackStack() }
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        return binding.root
    }

    fun loginUser() {
        if (TextUtils.isEmpty(binding.inputMobileNumber.text.toString()) || TextUtils.isEmpty(binding.inputMPIN.text.toString())) {
            Toast.makeText(context!!, "Please fill user credentials.", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.userLoginData(
                db,
                binding.inputMobileNumber.text.toString(),
                binding.inputMPIN.text.toString()
            )
            viewModel.userData.observe(viewLifecycleOwner) {
                if (it!!) {
                    val userPhoneNumber = binding.inputMobileNumber.text.toString()
                    val bundle = Bundle()
                    val profilePage = ProfilePageFragment()
                    bundle.putString(GET_USER_NUMBER, userPhoneNumber)
                    profilePage.arguments = bundle

                    Toast.makeText(context!!, "Login Successfully.", Toast.LENGTH_SHORT).show()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, profilePage).commit()
                } else {
                    Toast.makeText(context!!, "Wrong username and password.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}