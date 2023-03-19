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
import pack.logindemo.app.utils.Utils

class SignUpFragment : Fragment() {
    lateinit var binding: FragmentSignUpBinding
    lateinit var db: DBHelper
    lateinit var utils: Utils

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        db = DBHelper(context!!)
        utils = Utils()
        binding.regButton.setOnClickListener {
            registerUser()
        }
        binding.regBack.setOnClickListener { parentFragmentManager.popBackStack() }
        return binding.root
    }

    fun registerUser() {
        if (TextUtils.isEmpty(binding.regFirstName.text.toString()) || TextUtils.isEmpty(binding.regLastName.text.toString()) || TextUtils.isEmpty(binding.regMobileNumber.text.toString())
            || TextUtils.isEmpty(binding.regMPIN.text.toString()) || TextUtils.isEmpty(binding.regConfirmMPIN.text.toString())) {
            Toast.makeText(context!!, "Please fill all credentials field", Toast.LENGTH_LONG).show()
        } else if (!utils.pinMatch(context!!, binding.regMPIN.text.toString(), binding.regConfirmMPIN.text.toString())) {
            return
        } else {
            val userDTO = UserDTO(
                firstName = binding.regFirstName.text.toString(),
                lastName = binding.regLastName.text.toString(),
                mobileNumber = binding.regMobileNumber.text.toString(),
                mPin = binding.regMPIN.text.toString(),
                confirmMpin = binding.regConfirmMPIN.text.toString()
            )

            val checkNumber = db.checkNumberExists(binding.regMobileNumber.text.toString())
            if (checkNumber) {
                Toast.makeText(context!!, "Phone number exists", Toast.LENGTH_LONG).show()
                return
            } else {
                val saveData = db.insertUserData(userDTO)
                if (saveData) {
                    Toast.makeText(context!!, "Registered Successfully!", Toast.LENGTH_LONG)
                        .show()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, LoginFragment()).commit()
                } else {
                    Toast.makeText(context!!, "User credentials exists", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}