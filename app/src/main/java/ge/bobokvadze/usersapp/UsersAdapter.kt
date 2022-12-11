package ge.bobokvadze.usersapp

import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ge.bobokvadze.usersapp.databinding.UserItemBinding

class UsersAdapter : ListAdapter<String, UsersViewHolder>(UsersDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UsersViewHolder(
        UserItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: UsersViewHolder, position: Int
    ) = holder.bind(getItem(position))
}

class UsersViewHolder(
    private val binding: UserItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: String) = with(binding) {
        tvEmail.text = item
        tvName.text = item
    }
}

class UsersDiffCallBack : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
}