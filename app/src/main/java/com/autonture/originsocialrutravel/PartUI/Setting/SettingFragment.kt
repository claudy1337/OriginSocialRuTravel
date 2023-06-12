package com.autonture.originsocialrutravel.PartUI.Setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    private fun init(id:Int, login:String){
        Toast.makeText(requireContext(), "Четко ${id} ${login}", Toast.LENGTH_SHORT).show()
        val getUserCall = login?.let { ConnectionService().service().getId(id) }
        getUserCall?.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    // Обработка полученного пользователя
                    if (user != null) {
                        // Вывод имени пользователя
                        binding.userNameText.setText(user.name)
                        binding.userLastText.setText(user.surname)

                    } else {
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var id = PrefsManager(requireContext()).getUserId()
        var login = PrefsManager(requireContext()).getUserLogin()
        if (id!= null || login != null){
            login?.let { id?.let { it1 -> init(it1, it) } }
        }
        binding.imgUser.setOnClickListener {

        }
        binding.saveBtn.setOnClickListener {
            var code = binding.userCodeText.text
            if(binding.userNameText.text != null || binding.userLastText.text != null || binding.userPasswordText.text != null){

                if (code != null){
                    PrefsManager(requireContext()).deleteCode()
                    PrefsManager(requireContext()).saveCode(code.toString())
                }
            }
        }
        binding.codeCheck.setOnCheckedChangeListener{ buttonView, isChecked ->
            binding.userCodeText.isEnabled = isChecked
            PrefsManager(requireContext()).setCode(isChecked)
        }

    }
    companion object {

        @JvmStatic
        fun newInstance() = SettingFragment()
    }
}