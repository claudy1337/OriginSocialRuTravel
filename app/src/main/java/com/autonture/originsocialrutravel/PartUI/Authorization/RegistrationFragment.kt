package com.autonture.originsocialrutravel.PartUI.Authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.autonture.originsocialrutravel.MAIN
import com.autonture.originsocialrutravel.R
import com.autonture.originsocialrutravel.Utilis.Classes.User
import com.autonture.originsocialrutravel.Utilis.ConnectionService
import com.autonture.originsocialrutravel.Utilis.PrefsManager
import com.autonture.originsocialrutravel.databinding.FragmentRegistrationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.authAccTxt.setOnClickListener {
            MAIN.navController.navigate(R.id.action_registrationFragment_to_signIn)
        }
        binding.authBtn.setOnClickListener {
            val login = binding.userLoginText.text.toString()
            val name = binding.userNameText.text.toString()
            val lastName = binding.userLastText.text.toString()
            val password = binding.userPasswordText.text.toString()
            val email = binding.userEmailText.text.toString()
            if (login.isEmpty() || name.isEmpty() || password.isEmpty() || email.isEmpty())
            {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
            else{

                val checkUserExistenceCall = ConnectionService().service().getLogin(login)
                checkUserExistenceCall.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "Пользователь с таким логином уже существует", Toast.LENGTH_SHORT).show()
                        } else {
                            val user = User(null ,name, lastName, login, password, email, 0, 5.0, null)

                            val createUserCall = ConnectionService().service().createUser(user)
                            createUserCall.enqueue(object : Callback<Void> {
                                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                    if (response.isSuccessful) {
                                        PrefsManager(requireContext()).deleteCode()
                                        PrefsManager(requireContext()).deleteUserId()
                                        PrefsManager(requireContext()).deleteUserLogin()
                                        PrefsManager(requireContext()).setCode(false)
                                        PrefsManager(requireContext()).setLoginig(false)
                                        Toast.makeText(requireContext(), "Пользователь успешно создан", Toast.LENGTH_SHORT).show()
                                        findNavController().navigate(R.id.action_registrationFragment_to_signIn)

                                    } else {
                                        Toast.makeText(requireContext(), "Ошибка создания пользователя", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onFailure(call: Call<Void>, t: Throwable) {
                                    Toast.makeText(requireContext(), "Ошибка сети: ${t.message}", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }
                    }
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(requireContext(), "Ошибка сети: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegistrationFragment()
    }
}