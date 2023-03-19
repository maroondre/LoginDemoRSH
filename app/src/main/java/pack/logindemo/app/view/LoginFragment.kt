package pack.logindemo.app.view

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import pack.logindemo.app.R
import pack.logindemo.app.databinding.FragmentLoginBinding
import pack.logindemo.app.sqlite.DBHelper
import pack.logindemo.app.utils.Utils

class LoginFragment : Fragment() {
    lateinit var binding : FragmentLoginBinding
    lateinit var db : DBHelper
    lateinit var utils: Utils

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        utils = Utils()
        utils.checkEmptyField(binding.login, null, null, binding.inputMobileNumber, binding.inputMPIN, null)
        binding.login.setOnClickListener {
            loginUser()
        }
        binding.back.setOnClickListener { parentFragmentManager.popBackStack() }
        db = DBHelper(context!!)
        return binding.root
    }

    fun loginUser() {
        if (TextUtils.isEmpty(binding.inputMobileNumber.text.toString()) || TextUtils.isEmpty(binding.inputMPIN.text.toString())) {
            Toast.makeText(context!!, "Please fill user credentials.", Toast.LENGTH_LONG).show()
        } else {
            val logData = db.checkCredentials(binding.inputMobileNumber.text.toString(), binding.inputMPIN.text.toString())
            val userPhoneNumber = binding.inputMobileNumber.text.toString()
            if (logData){
                val bundle = Bundle()
                val profilePage = ProfilePageFragment()
                bundle.putString(GET_USER_NUMBER, userPhoneNumber)
                profilePage.arguments = bundle

                Toast.makeText(context!!, "Login Successfully.", Toast.LENGTH_LONG).show()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, profilePage).commit()
            } else {
                Toast.makeText(context!!, "Wrong username and password.", Toast.LENGTH_LONG).show()
            }
        }
    }
}