package com.example.mymiso.presentation.search_screen

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymiso.databinding.ItemSearchResultBinding
import com.example.mymiso.domain.SearchResult

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

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        Log.d("SearchResultAdapter", "onBindViewHolder: $position")
        val searchResult = differ.currentList[position]
        holder.binding.apply {
            itemName.text = searchResult.title
            itemDescription.text = searchResult.description
            Glide.with(this.root).load(searchResult.image).into(restaurantSearchImage)
        }
    }

    private val diffUtilCallBack = object : DiffUtil.ItemCallback<SearchResult>() {
        override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffUtilCallBack)

}