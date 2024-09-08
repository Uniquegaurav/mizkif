package com.example.mymiso.presentation.landing_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymiso.databinding.ItemRecommendedRestaurantBinding
import com.example.mymiso.domain.model.Restaurant

class RecommendedRestaurantRecyclerViewAdapter :
    RecyclerView.Adapter<RecommendedRestaurantRecyclerViewAdapter.RecommendedRestaurantViewHolder>() {

    inner class RecommendedRestaurantViewHolder(val binding: ItemRecommendedRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendedRestaurantViewHolder {
        val binding =
            ItemRecommendedRestaurantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return RecommendedRestaurantViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RecommendedRestaurantViewHolder, position: Int) {
        val restaurant = differ.currentList[position]
        holder.binding.apply {
            restaurantName.text = restaurant.name
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