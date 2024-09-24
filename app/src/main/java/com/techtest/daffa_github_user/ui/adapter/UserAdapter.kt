package com.techtest.daffa_github_user.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.techtest.daffa_github_user.R
import com.techtest.daffa_github_user.databinding.ItemUserBinding
import com.techtest.daffa_github_user.domain.model.User
import com.techtest.daffa_github_user.util.setImageUrl

class UserAdapter(val callback: (String) -> Unit = {}) :
    ListAdapter<User, UserAdapter.VerticalViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VerticalViewHolder(view)
    }

    override fun onBindViewHolder(holder: VerticalViewHolder, position: Int) {
        val game = getItem(position)
        if (game != null) {
            holder.bind(game)
        }
    }

    inner class VerticalViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: User) {
            binding.apply {
                ivUser.setImageUrl(
                    itemView.context,
                    data.avatarUrl,
                    pbUser,
                    R.drawable.ic_no_image
                )

                tvUsername.text = data.username

                itemView.setOnClickListener {
                    callback.invoke(data.username)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<User> =
            object : DiffUtil.ItemCallback<User>() {
                override fun areItemsTheSame(
                    oldUser: User,
                    newUser: User
                ): Boolean {
                    return oldUser.username == newUser.username
                }

                override fun areContentsTheSame(
                    oldUser: User,
                    newUser: User
                ): Boolean {
                    return oldUser == newUser
                }
            }
    }
}