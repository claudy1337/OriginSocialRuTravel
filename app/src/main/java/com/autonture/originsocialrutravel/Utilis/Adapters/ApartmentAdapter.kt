package com.autonture.originsocialrutravel.Utilis.Adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.autonture.originsocialrutravel.Utilis.Classes.Apartment
import com.autonture.originsocialrutravel.databinding.ApartamentItemBinding

class ApartmentAdapter (val context: Context, val myApartments: ArrayList<Apartment>): RecyclerView.Adapter<ApartmentAdapter.ApartmentHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApartmentHolder {
        return ApartmentHolder(ApartamentItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ApartmentHolder, position: Int) {
        val post = myApartments[position]
        holder.bind(post)

    }

    override fun getItemCount(): Int = myApartments.size

    class ApartmentHolder(val binding: ApartamentItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Apartment) {
            binding.apartmentAddress.text = "Адрес:${post.address}"
            binding.apartmentCity.text = "Комнат:${post.countOfRooms}"
            binding.apartmentRating.text = post.rating.toString()
            binding.apartmentPrice.text = "Цена:${post.pricePerMonth.toString()}р"
            if (post.Photos != null) {
                binding.apartmentImg.setImageBitmap(post.Photos[0].photo?.let { decodeBase64ToBitmap(it) })
            }
        }


        private fun decodeBase64ToBitmap(base64String: String): Bitmap {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        }
    }

    fun updatePost(post: Apartment) {
        myApartments[myApartments.indexOf(post)] = post
        notifyItemChanged(myApartments.indexOf(post))
    }
}