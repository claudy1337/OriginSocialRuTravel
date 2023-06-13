package com.autonture.originsocialrutravel.Utilis.Adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.autonture.originsocialrutravel.Utilis.Classes.Apartment
import com.autonture.originsocialrutravel.Utilis.Classes.Post
import com.autonture.originsocialrutravel.databinding.PostItemBinding

class PostAdapter (val context: Context, val myPosts: ArrayList<Post>, val onClick: (Post) -> Unit ={}): RecyclerView.Adapter<PostAdapter.PostHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        return PostHolder(PostItemBinding.inflate(LayoutInflater.from(context), parent, false))

    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = myPosts[position]
        holder.bind(post)
        holder.binding.root.setOnClickListener {
            onClick(post)
        }

    }

    override fun getItemCount(): Int = myPosts.size

    class PostHolder(val binding: PostItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            if (post.User != null){
                binding.userLoginTxt.setText(post.User.email)
                binding.userNameTxt.setText("${post.User.name} ${post.User.surname}")
                binding.titleTxt.setText(post.title)
                binding.textPostTxt.setText(post.text)
                binding.datePost.setText(post.date)
                val dsf = post.Photos[0].photo
            }
            if (post.Photos != null) {
                binding.imagePost.setImageBitmap(post.Photos[0].photo?.let { decodeBase64ToBitmap(it) })
            }
            else{
                binding.imagePost.setImageBitmap(null)
            }
        }
        private fun decodeBase64ToBitmap(base64String: String): Bitmap {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        }
    }

    fun updatePost(post: Post) {
        myPosts[myPosts.indexOf(post)] = post
        notifyItemChanged(myPosts.indexOf(post))
    }
}