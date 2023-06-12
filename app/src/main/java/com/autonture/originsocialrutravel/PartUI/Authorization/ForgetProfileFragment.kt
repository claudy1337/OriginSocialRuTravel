package com.autonture.originsocialrutravel.PartUI.Authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.autonture.originsocialrutravel.MAIN
import com.autonture.originsocialrutravel.R
import com.autonture.originsocialrutravel.Utilis.Classes.User
import com.autonture.originsocialrutravel.Utilis.ConnectionService
import com.autonture.originsocialrutravel.databinding.FragmentForgetProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ForgetProfileFragment : Fragment() {
    private lateinit var binding: FragmentForgetProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgetProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.authAccTxt.setOnClickListener {
            MAIN.navController.navigate(R.id.action_forgetProfileFragment_to_signIn)
        }
        binding.sendBtn.setOnClickListener {
            val login = binding.userLoginText.text.toString()
            val email = binding.userEmailText.text.toString()
            if (login.isEmpty() || email.isEmpty()){
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
            else{
                val getUserCall = ConnectionService().service().getLogin(login)
                getUserCall.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            val user = response.body()
                            // Обработка полученного пользователя
                            if (user != null) {
                                // Вывод имени пользователя
                                if (user.email == email){
                                    Toast.makeText(requireContext(), "Письмо отправлено: ${user.name}", Toast.LENGTH_SHORT).show()
                                }
                                else{
                                    Toast.makeText(requireContext(), "Неверные данные", Toast.LENGTH_SHORT).show()
                                }

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
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ForgetProfileFragment()
    }
}