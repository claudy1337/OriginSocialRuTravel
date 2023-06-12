package com.autonture.originsocialrutravel.Utilis.Adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.autonture.originsocialrutravel.Utilis.Classes.Town
import com.autonture.originsocialrutravel.databinding.CityItemBinding

class TownAdapter (val context: Context, val myTowns: ArrayList<Town>): RecyclerView.Adapter<TownAdapter.TownHolder>(){
    var onItemClick: ((Town) -> Unit)? = null

    class TownHolder(val binding: CityItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Town) {
            binding.nameCity.text = post.name
            binding.titleCity.text = "${post.description}..."
            binding.ratingCity.text = post.rating.toString()
            if (post.Photos != null) {
                binding.imgCity.setImageBitmap(post.Photos[0].photo?.let { decodeBase64ToBitmap(it) })
            }
        }
        private fun decodeBase64ToBitmap(base64String: String): Bitmap {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        }
    }

    fun updatePost(post: Town) {
        myTowns[myTowns.indexOf(post)] = post
        notifyItemChanged(myTowns.indexOf(post))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TownHolder {
        return TownHolder(CityItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int = myTowns.size

    override fun onBindViewHolder(holder: TownHolder, position: Int) {
        val post = myTowns[position]
        holder.bind(post)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(post)
        }
    }
}