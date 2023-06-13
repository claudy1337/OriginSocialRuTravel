package com.autonture.originsocialrutravel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.autonture.originsocialrutravel.Utilis.Classes.SendPost
import com.autonture.originsocialrutravel.Utilis.ConnectionService
import com.autonture.originsocialrutravel.Utilis.PrefsManager
import com.autonture.originsocialrutravel.databinding.FragmentCreatePostBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class CreatePostFragment : Fragment() {
    private lateinit var binding: FragmentCreatePostBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var user = PrefsManager(requireContext()).getUserId()
        binding.sendBtn.setOnClickListener {
            val currentDate = getCurrentDate()
            if (binding.textPostText.text != null || user != null || binding.titlePostText.text != null){
                val post = SendPost(binding.titlePostText.text?.toString(),
                    binding.textPostText.text?.toString(), user, currentDate)

                val call = ConnectionService().service().sendPost(post)
                call.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "Пост добавлен", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_createPostFragment_to_userProfileFragment)
                        } else {
                            // Обработка ошибки
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        // Обработка ошибки сети
                    }
                })
            }
        }
    }

    fun getCurrentDate(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(currentDate)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatePostBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CreatePostFragment()
    }
}