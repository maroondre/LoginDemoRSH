package pack.logindemo.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import pack.logindemo.app.R
import pack.logindemo.app.databinding.ItemsListOfRewardsBinding
import pack.logindemo.app.model.RewardsDTO

class ProfileAdapter(val list: List<RewardsDTO>, val clickItem : passData) :
    RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemsListOfRewardsBinding) : RecyclerView.ViewHolder(binding.root) {
        var bindings: ItemsListOfRewardsBinding = binding

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding: ItemsListOfRewardsBinding = DataBindingUtil.inflate(
            view,
            R.layout.items_list_of_rewards,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileAdapter.ViewHolder, position: Int) {
        val data = list[position]

        holder.bindings.rewardDesc.text = data.description
        Picasso.get().load(data.image).into(holder.bindings.rewardLogo)
        holder.bindings.root.setOnClickListener { clickItem.passData(data.name, data.description, data.image) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface passData {
        fun passData(title: String?, description: String, imageURL: String)
    }
}