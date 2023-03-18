package pack.logindemo.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import pack.logindemo.app.R
import pack.logindemo.app.databinding.ItemsListOfRewardsBinding
import pack.logindemo.app.model.RewardsDTO

class ProfileAdapter(val list: List<RewardsDTO>) :
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
    }

    override fun getItemCount(): Int {
        return list.size
    }
}