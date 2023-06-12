package com.autonture.originsocialrutravel.PartUI.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.autonture.originsocialrutravel.MAIN
import com.autonture.originsocialrutravel.R
import com.autonture.originsocialrutravel.Utilis.Adapters.PostAdapter
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
        // PrefsManager(requireContext())
        init()
        MAIN.navController.navigate(R.id.action_userProfileFragment_to_postsFragment)
    }

    private fun init(){
        val login = PrefsManager(requireContext()).getUserId()
        val getUserCall = login?.let { ConnectionService().service().getId(it) }
        getUserCall?.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    // Обработка полученного пользователя
                    if (user != null) {
                        // Вывод имени пользователя
                        binding.userNameTxt.text = "${user.name} ${user.surname}"
                        binding.userEmailTxt.text = user.email
                        binding.countTravel.text = user.countOfTravels.toString()
                        binding.rating.text = user.rating.toString()

                    } else {
                        Toast.makeText(requireContext(), "Пользователь не найден", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Ошибка получения данных
                    Toast.makeText(requireContext(), "Ошибка получения данных обновитесь", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                // Обработка ошибки
                Toast.makeText(requireContext(), "Ошибка сети обновитесь: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {

        @JvmStatic
        fun newInstance() = UserProfileFragment()
    }
}