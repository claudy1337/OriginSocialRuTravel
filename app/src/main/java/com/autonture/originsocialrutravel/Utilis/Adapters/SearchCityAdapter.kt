package com.autonture.originsocialrutravel.Utilis.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.autonture.originsocialrutravel.R
import com.autonture.originsocialrutravel.Utilis.Classes.Apartment
import com.autonture.originsocialrutravel.Utilis.Classes.Town
import com.autonture.originsocialrutravel.databinding.SearchCityItemBinding

class SearchCityAdapter: ListAdapter<Town, SearchCityAdapter.Holder>(Comparator()) {
    class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = SearchCityItemBinding.bind(view)
        fun bind(town:Town) = with(binding){
            cityName.text = town.name
            cityTitle.text = town.description
        }
    }
    class Comparator: DiffUtil.ItemCallback<Town>(){
        override fun areItemsTheSame(oldItem: Town, newItem: Town): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Town, newItem: Town): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_city_item, parent, false)
        return  Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}