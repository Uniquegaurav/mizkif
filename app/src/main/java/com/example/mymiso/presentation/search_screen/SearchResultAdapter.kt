package com.example.mymiso.presentation.search_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymiso.databinding.ItemSearchResultBinding
import com.example.mymiso.domain.model.SearchResult
class SearchResultAdapter : RecyclerView.Adapter<SearchResultAdapter.SearchViewHolder>() {
    inner class SearchViewHolder(val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        Log.d("SearchResultAdapter", "onCreateViewHolder: ")
        val binding =
            ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        Log.d("SearchResultAdapter", "getItemCount: ${differ.currentList.size}")
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int, payloads: MutableList<Any>) {
        Log.d("SearchResultAdapter", "onBindViewHolder with payload: $position")
        val searchResult = differ.currentList[position]

        if (payloads.isNotEmpty()) {
            // Partial update
            val diffBundle = payloads[0] as Bundle
            diffBundle.keySet().forEach { key ->
                when (key) {
                    "title" -> holder.binding.itemName.text = diffBundle.getString(key)
                    "description" -> holder.binding.itemDescription.text = diffBundle.getString(key)
                    "image" -> {
                        val imageUrl = diffBundle.getString(key)
                        Glide.with(holder.binding.root).load(imageUrl).into(holder.binding.restaurantSearchImage)
                    }
                }
            }
        } else {
            // Full binding when there's no payload
            holder.binding.apply {
                itemName.text = searchResult.title
                itemDescription.text = searchResult.description
                Glide.with(this.root).load(searchResult.image).into(restaurantSearchImage)
            }
        }
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        // Fallback to default full bind if payloads are empty
        onBindViewHolder(holder, position, mutableListOf())
    }

    private val diffUtilCallBack = object : DiffUtil.ItemCallback<SearchResult>() {
        override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: SearchResult, newItem: SearchResult): Any? {
            val diffBundle = Bundle()

            if (oldItem.title != newItem.title) {
                diffBundle.putString("title", newItem.title)
            }
            if (oldItem.description != newItem.description) {
                diffBundle.putString("description", newItem.description)
            }
            if (oldItem.image != newItem.image) {
                diffBundle.putString("image", newItem.image)
            }

            return if (diffBundle.size() == 0) null else diffBundle
        }
    }

    val differ = AsyncListDiffer(this, diffUtilCallBack)
}
