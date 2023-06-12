package com.autonture.originsocialrutravel.PartUI.Authorization

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.navigation.fragment.findNavController
import com.autonture.originsocialrutravel.MAIN
import com.autonture.originsocialrutravel.MainActivity
import com.autonture.originsocialrutravel.R
import com.autonture.originsocialrutravel.Utilis.Classes.User
import com.autonture.originsocialrutravel.Utilis.ConnectionService
import com.autonture.originsocialrutravel.Utilis.PrefsManager
import com.autonture.originsocialrutravel.databinding.FragmentSignInBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtRegistration.setOnClickListener {
            MAIN.navController.navigate(R.id.action_signIn_to_registrationFragment)
        }
        binding.txtForgetPass.setOnClickListener {
            MAIN.navController.navigate(R.id.action_signIn_to_forgetProfileFragment)
        }
        binding.authBtn.setOnClickListener {
            val login = binding.userLoginText.text.toString()
            val password = binding.userPasswordText.text.toString()

            val getUserCall = ConnectionService().service().getLogin(login)
            if (login.isEmpty() || password.isEmpty()){
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
            else{
                getUserCall.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            val user = response.body()
                            // Обработка полученного пользователя
                            if (user != null) {
                                // Вывод имени пользователя
                                if (user.password == password){
                                    PrefsManager(requireContext()).deleteCode()
                                    PrefsManager(requireContext()).deleteUserId()
                                    PrefsManager(requireContext()).deleteUserLogin()
                                    PrefsManager(requireContext()).setCode(false)

                                    Toast.makeText(requireContext(), "Добро пожаловать: ${user.name}", Toast.LENGTH_SHORT).show()
                                    PrefsManager(requireContext()).saveUserLogin(user.login)
                                    user.id?.let { it1 -> PrefsManager(requireContext()).saveUserId(it1) }
                                    PrefsManager(requireContext()).setLoginig(true)

                                    findNavController().navigate(R.id.action_signIn_to_userProfileFragment)
                                }
                                else{
                                    Toast.makeText(requireContext(), "Неверный пароль", Toast.LENGTH_SHORT).show()
                                }

                            } else {
                                // Пользователь не найден
                                Toast.makeText(requireContext(), "Пользователь не найден", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // Ошибка получения данных
                            Toast.makeText(requireContext(), "Ошибка получения данных", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        // Обработка ошибки
                        Toast.makeText(requireContext(), "Ошибка сети: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }

           // PrefsManager(requireContext()).setLogged(true)
          //  recreate(requireActivity())

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignInFragment()
    }
}