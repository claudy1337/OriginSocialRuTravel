package com.autonture.originsocialrutravel.PartUI.Profile

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.autonture.originsocialrutravel.MAIN
import com.autonture.originsocialrutravel.R
import com.autonture.originsocialrutravel.Utilis.Adapters.PostAdapter
import com.autonture.originsocialrutravel.Utilis.Classes.Photo
import com.autonture.originsocialrutravel.Utilis.Classes.Post
import com.autonture.originsocialrutravel.Utilis.Classes.User
import com.autonture.originsocialrutravel.Utilis.ConnectionService
import com.autonture.originsocialrutravel.Utilis.PrefsManager
import com.autonture.originsocialrutravel.Utilis.ViewModels.PostViewModel
import com.autonture.originsocialrutravel.databinding.FragmentUserProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserProfileFragment : Fragment() {
    private lateinit var binding:FragmentUserProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = PrefsManager(requireContext()).getUserId()
        val userLogin = PrefsManager(requireContext()).getUserLogin()

        userId?.let { init(it) }
        userId?.let { setImage(it) }
        binding.buttonProfile.setOnClickListener {
            findNavController().navigate(R.id.action_userProfileFragment_to_createPostFragment)
        }
    }

    private fun init(id:Int){
        val call = ConnectionService().service().getId(id)
        call.enqueue(object : Callback<User> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                        binding.countTravel.setText(user?.countOfTravels.toString())
                        binding.userNameTxt.setText("${user?.name} ${user?.surname}")
                        binding.rating.setText("${user?.rating}")
                        binding.userEmailTxt.setText("${user?.email}")
                        binding.posts.setText("0")
                } else {
                    // Обработка ошибки
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                // Обработка ошибки
            }
        })
    }
    fun decodeBase64ToBitmap(base64String: String): Bitmap {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }
    private fun setImage(idUser:Int){
        val call = ConnectionService().service().getUserPhoto(idUser)
        call.enqueue(object : Callback<List<Photo>> {
            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                if (response.isSuccessful) {
                    val dataList = response.body()
                    if (dataList != null && dataList.isNotEmpty()) {
                        val firstData = dataList[0]
                        val bitmap = firstData.photo?.let { decodeBase64ToBitmap(it) }
                        binding.userImage.setImageBitmap(bitmap)
                        // Выведите другие свойства элемента под индексом 0

                    } else {
                        // Список пуст или null
                    }
                } else {
                    // Обработка ошибки
                }
            }

            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                // Обработка ошибки
            }
        })
    }


    companion object {

        @JvmStatic
        fun newInstance() = UserProfileFragment()
    }
}