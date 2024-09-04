package com.example.mymiso.presentation.landing_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.mymiso.databinding.ItemAllRestaurantBinding
import com.example.mymiso.presentation.landing_screen.model.Restaurant

class AllRestaurantRecyclerViewAdapter :
    RecyclerView.Adapter<AllRestaurantRecyclerViewAdapter.AllRestaurantViewHolder>() {
    inner class AllRestaurantViewHolder(val binding: ItemAllRestaurantBinding) :
        ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllRestaurantViewHolder {
        val binding =
            ItemAllRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllRestaurantViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: AllRestaurantViewHolder, position: Int) {
        val restaurant = differ.currentList[position]
        holder.binding.apply {
            restaurantName.text = restaurant.name
            restaurantCost.text = ""
            restaurantRating.text = restaurant.rating.toString()
            Glide.with(this.root).load(restaurant.image).into(restaurantImage)
            root.setOnClickListener {
                onItemClickListener?.let {
                    it(restaurant)
                }
            }
        }
    }

    private var onItemClickListener: ((Restaurant) -> Unit)? = null

    fun setOnItemClickListener(listener: (Restaurant) -> Unit) {
        onItemClickListener = listener
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Restaurant>() {
        override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallBack)

}