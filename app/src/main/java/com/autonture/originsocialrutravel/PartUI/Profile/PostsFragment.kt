package com.autonture.originsocialrutravel.PartUI.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.autonture.originsocialrutravel.R
import com.autonture.originsocialrutravel.Utilis.Adapters.PostAdapter
import com.autonture.originsocialrutravel.Utilis.Classes.Post
import com.autonture.originsocialrutravel.Utilis.ConnectionService
import com.autonture.originsocialrutravel.Utilis.PrefsManager
import com.autonture.originsocialrutravel.Utilis.ViewModels.PostViewModel
import com.autonture.originsocialrutravel.databinding.FragmentPostsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PostsFragment : Fragment() {
    private lateinit var binding: FragmentPostsBinding
    private lateinit var posts: List<Post>
    private lateinit var adapterPost: PostAdapter
    private val viewModel: PostViewModel by viewModels()
    private var deleted = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        var idUser = PrefsManager(requireContext()).getUserId()
        if (idUser != null){
            viewModel.getPosts(idUser)
        }

    }

    private fun setUpObservers() {
        viewModel.posts.observe(viewLifecycleOwner) { posts ->
            this.posts = posts
            adapterPost = PostAdapter(requireContext(), posts as ArrayList<Post>, onClick = {
                val alertDialog = AlertDialog.Builder(requireContext())
                .setTitle("Удалить пост")
                .setMessage("Описание: ${it.title}")
                .setPositiveButton("ОК") { dialog, _ ->
                    deleted = true
                    it.id?.let { it1 -> deletePost(it1) }
                    dialog.dismiss()
        }
        .create()
    alertDialog.show()
            })
            with(binding.postList) {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = adapterPost
            }
        }
        viewModel.post.observe(viewLifecycleOwner) { post ->
            updatePost(post)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
    private fun deletePost(id:Int){
        val deleteUserCall = ConnectionService().service().deletePost(id)
        deleteUserCall.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Пост удален", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_postsFragment2_self)
                } else {
                    // Обработка ошибки сервера
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Обработка ошибки сети
            }
        })
    }

    private fun updatePost(post: Post) {
        adapterPost.updatePost(post)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PostsFragment()
    }
}