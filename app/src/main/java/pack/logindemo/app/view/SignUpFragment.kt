package pack.logindemo.app.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import pack.logindemo.app.R
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
        utils.checkEmptyField(binding.regButton, binding.regFirstName, binding.regLastName, binding.regMobileNumber, binding.regMPIN, binding.regConfirmMPIN)
        binding.regButton.setOnClickListener {
            registerUser()
        }
        binding.regBack.setOnClickListener { parentFragmentManager.popBackStack() }
        return binding.root
    }

    fun registerUser() {
        if (!utils.pinMatch(context!!, binding.regMPIN.text.toString(), binding.regConfirmMPIN.text.toString())) {
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