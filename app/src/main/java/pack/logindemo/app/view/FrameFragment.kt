package pack.logindemo.app.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import pack.logindemo.app.R
import pack.logindemo.app.databinding.ActivityFrameFragmentBinding
import kotlin.system.exitProcess


class FrameFragment : Fragment() {
    lateinit var binding: ActivityFrameFragmentBinding
    var doubleBackToExitPressedOnce = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.activity_frame_fragment, container, false)

        binding.loginbtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, LoginFragment()).addToBackStack(null).commit()
        }
        binding.registerbtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, SignUpFragment()).addToBackStack(null).commit()
        }
        return binding.root
    }


    fun onBackPressed() {
        when (childFragmentManager.findFragmentById(R.id.frameLayout)) {
            is LoginFragment, is SignUpFragment -> {
                parentFragmentManager.popBackStack()
            }
            is ProfilePageFragment, is FrameFragment -> {
                if (doubleBackToExitPressedOnce) {
                    exitProcess(1)
                }

                this.doubleBackToExitPressedOnce = true
                Toast.makeText(activity, "Press back again to exit app", Toast.LENGTH_SHORT)
                    .show()
                Handler(Looper.getMainLooper()).postDelayed({
                    doubleBackToExitPressedOnce = false
                }, 2000)
            }
        }
    }
}