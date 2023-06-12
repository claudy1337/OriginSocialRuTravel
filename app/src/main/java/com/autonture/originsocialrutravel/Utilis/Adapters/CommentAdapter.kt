package com.autonture.originsocialrutravel.Utilis.Adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.autonture.originsocialrutravel.Utilis.Classes.Comments
import com.autonture.originsocialrutravel.databinding.CommentItemBinding

class CommentAdapter (val context: Context, val myComments: ArrayList<Comments>): RecyclerView.Adapter<CommentAdapter.CommentHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        return CommentHolder(CommentItemBinding.inflate(LayoutInflater.from(context), parent, false))

    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        val comm = myComments[position]
        holder.bind(comm)
    }

    override fun getItemCount(): Int = myComments.size

    class CommentHolder(val binding: CommentItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(comm: Comments) {
            binding.textComm.text = comm.text
            binding.dateComm.text = comm.date
            if (comm.User != null) {
                binding.textView18.text = comm.User.email
                binding.textView8.text = "${comm.User.name} ${comm.User.surname}"
            }
        }
    }

    fun updatePost(comm: Comments) {
        myComments[myComments.indexOf(comm)] = comm
        notifyItemChanged(myComments.indexOf(comm))
    }
}