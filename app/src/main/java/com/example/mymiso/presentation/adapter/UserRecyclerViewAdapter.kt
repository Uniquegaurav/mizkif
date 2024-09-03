package com.example.mymiso.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymiso.databinding.UserEachRowBinding
import com.example.mymiso.presentation.model.User

class UserRecyclerViewAdapter : RecyclerView.Adapter<UserRecyclerViewAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: UserEachRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {

        val binding = UserEachRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = differ.currentList[position]
        holder.binding.apply {
            nameTextView.text = user.firstName
            emailTextView.text = user.email
            Glide.with(this.root).load(user.image).into(imageView)
            root.setOnClickListener {
                onClickListener?.let {
                    it(user)
                }
            }
        }

    }

    private var onClickListener: ((User) -> Unit)? = null
    fun setOnItemClickListener(listener: (User) -> Unit) {
        onClickListener = listener
    }

    private val differCallBack = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)


}