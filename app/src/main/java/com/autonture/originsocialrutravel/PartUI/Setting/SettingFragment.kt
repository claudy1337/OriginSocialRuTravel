package com.autonture.originsocialrutravel.PartUI.Setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.autonture.originsocialrutravel.R
import com.autonture.originsocialrutravel.Utilis.Classes.User
import com.autonture.originsocialrutravel.Utilis.ConnectionService
import com.autonture.originsocialrutravel.Utilis.PrefsManager
import com.autonture.originsocialrutravel.databinding.FragmentSettingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log


class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var userPut:User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    private fun init(id:Int, login:String){
        val getUserCall = login?.let { ConnectionService().service().getLogin(login) }
        getUserCall?.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    // Обработка полученного пользователя
                    if (user != null) {
                        // Вывод имени пользователя
                        binding.userNameText.setText(user.name)
                        binding.userLastText.setText(user.surname)
                        userPut = user


                    } else {
                        Toast.makeText(requireContext(), "Пользователь не найден", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Ошибка получения данных оновитесь", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(requireContext(), "Ошибка сети обновитесь: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })


    }
    private fun putUser(id: Int, name:String,lastname:String, login:String, password:String, email:String, countOfTravels:Int, rating:Double, townsRefID:Int){
        val user = User(id, name, lastname, login, password, email, countOfTravels, rating, townsRefID)

        val call = ConnectionService().service().updateUser(1, user)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val updatedUser = response.body()
                    Toast.makeText(requireContext(), "Успешно", Toast.LENGTH_SHORT).show()
                    if (binding.userNameText.text != null || binding.userLastText.text != null){
                        user.countOfTravels?.let {
                            user.rating?.let { it1 ->
                                user.townsRefID?.let { it2 ->
                                    putUser(id, binding.userNameText.text.toString(),
                                        binding.userLastText.text.toString(), login,
                                        user.password, user.email, it, it1, it2
                                    )
                                }
                            }
                        }
                    }
                    findNavController().navigate(R.id.action_userSettingFragment_to_anonymousSettingFragment)

                } else {
                    Toast.makeText(requireContext(), "Ошибка получения данных оновитесь", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(requireContext(), "Ошибка сети обновитесь:", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var id = PrefsManager(requireContext()).getUserId()
        var login = PrefsManager(requireContext()).getUserLogin()

        binding.imgUser.setOnClickListener {

        }
        binding.saveBtn.setOnClickListener {
            var code = binding.userCodeText.text
            if(binding.userNameText.text != null || binding.userLastText.text != null || binding.userPasswordText.text != null){
                PrefsManager(requireContext()).deleteCode()
                if (code != null){
                    PrefsManager(requireContext()).saveCode(code.toString())
                }
                if (id!= null || login != null){
                    //putUser()

                }
            }
        }
        binding.codeCheck.setOnCheckedChangeListener{ buttonView, isChecked ->
            binding.userCodeText.isEnabled = isChecked
            PrefsManager(requireContext()).setCode(isChecked)
        }
        binding.logout.setOnClickListener {
            PrefsManager(requireContext()).deleteCode()
            PrefsManager(requireContext()).deleteUserId()
            PrefsManager(requireContext()).deleteUserLogin()
            findNavController().navigate(R.id.action_userSettingFragment_to_anonymousSettingFragment)
        }

    }
    companion object {

        @JvmStatic
        fun newInstance() = SettingFragment()
    }
}