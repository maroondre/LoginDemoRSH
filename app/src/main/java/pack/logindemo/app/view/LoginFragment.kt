package pack.logindemo.app.view

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import pack.logindemo.app.R
import pack.logindemo.app.databinding.FragmentLoginBinding
import pack.logindemo.app.model.UserDTO
import pack.logindemo.app.sqlite.DBHelper

class LoginFragment : Fragment() {
    lateinit var binding : FragmentLoginBinding
    lateinit var db : DBHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
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
            if (logData){
                Toast.makeText(context!!, "Login Successfully.", Toast.LENGTH_LONG).show()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, ProfilePageFragment()).commit()
            } else {
                Toast.makeText(context!!, "Wrong username and password.", Toast.LENGTH_LONG).show()
            }
        }
    }

}