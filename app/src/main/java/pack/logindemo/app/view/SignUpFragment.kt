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
import pack.logindemo.app.databinding.FragmentSignUpBinding
import pack.logindemo.app.model.UserDTO
import pack.logindemo.app.sqlite.DBHelper

class SignUpFragment : Fragment() {

    lateinit var binding : FragmentSignUpBinding
    lateinit var db : DBHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.regButton.setOnClickListener {
            registerUser()
        }
        binding.regBack.setOnClickListener { parentFragmentManager.popBackStack() }
        db = DBHelper(context!!)
        return binding.root
    }

    fun registerUser() {
        if (TextUtils.isEmpty(binding.regFirstName.text.toString()) || TextUtils.isEmpty(binding.regLastName.text.toString()) || TextUtils.isEmpty(binding.regMobileNumber.text.toString())
            || TextUtils.isEmpty(binding.regMPIN.text.toString()) || TextUtils.isEmpty(binding.regConfirmMPIN.text.toString()) ) {
            Toast.makeText(context!!, "Please fill all credentials field", Toast.LENGTH_LONG).show()
        } else {
            val userDTO = UserDTO(binding.regFirstName.text.toString(), binding.regLastName.text.toString(), binding.regMobileNumber.text.toString(), binding.regMPIN.text.toString(), binding.regConfirmMPIN.text.toString())
            val saveData = db.insertUserData(userDTO)
            binding.regButton.isEnabled
            if (binding.regMPIN.text.toString() == binding.regConfirmMPIN.text.toString()){
                if (saveData) {
                    Toast.makeText(context!!, "Registered Successfully!", Toast.LENGTH_LONG).show()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, ProfilePageFragment()).commit()
                } else {
                    Toast.makeText(context!!, "Users credentials exists. Try different one.", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context!!, "MPIN does not match.", Toast.LENGTH_LONG).show()
            }
        }
    }
}