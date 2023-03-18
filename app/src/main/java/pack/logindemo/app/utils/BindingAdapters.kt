package pack.logindemo.app.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pack.logindemo.app.adapter.ProfileAdapter
import pack.logindemo.app.model.RewardsDTO

class BindingAdapters {
    @BindingAdapter("android:recyclerData")
    fun recyclerData(view: RecyclerView, list: ArrayList<RewardsDTO>) {
        view.adapter = ProfileAdapter(list)
        view.layoutManager = LinearLayoutManager(view.context)
    }
}