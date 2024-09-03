package com.example.mymiso.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mymiso.databinding.ItemAllRestaurantBinding
import com.example.mymiso.presentation.model.Restaurant

class RecommendedRestaurantRecyclerViewAdapter :
    RecyclerView.Adapter<RecommendedRestaurantRecyclerViewAdapter.RecommendedRestaurantViewHolder>() {

    inner class RecommendedRestaurantViewHolder(val binding: ItemAllRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendedRestaurantViewHolder {
        val binding =
            ItemAllRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendedRestaurantViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RecommendedRestaurantViewHolder, position: Int) {
        val restaurant = differ.currentList[position]
        holder.binding.apply {
            restaurantName.text = restaurant.name
            restaurantCost.text = ""
            restaurantRating.text = restaurant.rating.toString()
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


    private val differCallback = object : DiffUtil.ItemCallback<Restaurant>() {
        override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
}