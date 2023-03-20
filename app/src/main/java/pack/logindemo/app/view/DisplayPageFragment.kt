package pack.logindemo.app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.squareup.picasso.Picasso
import pack.logindemo.app.R
import pack.logindemo.app.databinding.FragmentDisplayPageBinding

class DisplayPageFragment : Fragment() {
    lateinit var binding: FragmentDisplayPageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_display_page, container, false)
        val title = arguments?.getString("title")
        println("ttttt $title")
        val desc = arguments?.getString("description")
        val image = arguments?.getString("imageurl")

        binding.title.text = title
        binding.desc.text = desc
        Picasso.get().load(image).into(binding.imageView)
        return binding.root
    }
}