package com.autonture.originsocialrutravel.Utilis.Adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.autonture.originsocialrutravel.Utilis.Classes.Place
import com.autonture.originsocialrutravel.Utilis.Classes.Town
import com.autonture.originsocialrutravel.databinding.CityItemBinding
import com.autonture.originsocialrutravel.databinding.PlaceItemBinding

class PlaceAdapter (val context: Context, val myPlaces: ArrayList<Place>, val onClick: (Place) -> Unit ={}): RecyclerView.Adapter<PlaceAdapter.PlaceHolder>(){
    var onItemClick: ((Place) -> Unit)? = null

    class PlaceHolder(val binding: PlaceItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Place) {
            binding.namePlace.text = post.title
            if (post.Photos != null) {
                binding.imgPlace.setImageBitmap(post.Photos[0].photo?.let { decodeBase64ToBitmap(it) })
           }
        }
        private fun decodeBase64ToBitmap(base64String: String): Bitmap {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        }
    }

    fun updatePost(place: Place) {
        myPlaces[myPlaces.indexOf(place)] = place
        notifyItemChanged(myPlaces.indexOf(place))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        return PlaceHolder(PlaceItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int = myPlaces.size

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        val post = myPlaces[position]
        holder.bind(post)
        holder.binding.root.setOnClickListener {
            onClick(post)
        }
    }
}