package pack.logindemo.app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import pack.logindemo.app.R
import pack.logindemo.app.databinding.FragmentLoginBinding
import pack.logindemo.app.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    lateinit var binding : FragmentSignUpBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.regButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, ProfilePageFragment()).commit()
        }
        binding.regBack.setOnClickListener { parentFragmentManager.popBackStack() }
        return binding.root
    }
}