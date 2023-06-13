package com.autonture.originsocialrutravel.PartUI.Setting

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.autonture.originsocialrutravel.R
import com.autonture.originsocialrutravel.Utilis.Classes.Photo
import com.autonture.originsocialrutravel.Utilis.Classes.PutUser
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
        val getUserCall = ConnectionService().service().getLogin(login)
        getUserCall.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        binding.userNameText.setText(user.name)
                        binding.userLastText.setText(user.surname)
                        user.id?.let { getUserImage(it) }
                    }
                } else {
                    // Обработка ошибки сервера
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                // Обработка ошибки сети
            }
        })
    }
    fun getUserImage(idUser:Int){
        val call = ConnectionService().service().getUserPhoto(idUser)
        call.enqueue(object : Callback<List<Photo>> {
            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                if (response.isSuccessful) {
                    val dataList = response.body()
                    if (dataList != null && dataList.isNotEmpty()) {
                        val firstData = dataList[0]
                        val bitmap = firstData.photo?.let { decodeBase64ToBitmap(it) }
                        binding.imgUser.setImageBitmap(bitmap)

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
    fun decodeBase64ToBitmap(base64String: String): Bitmap {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var id = PrefsManager(requireContext()).getUserId()
        var login = PrefsManager(requireContext()).getUserLogin()
        id?.let { login.toString()?.let { it1 -> init(it, it1) } }
        binding.codeCheck.setOnCheckedChangeListener{ buttonView, isChecked ->
            binding.userCodeText.isEnabled = isChecked
            PrefsManager(requireContext()).setCode(isChecked)
        }
        binding.imgUser.setOnClickListener {

        }
        binding.saveBtn.setOnClickListener {
            var code = binding.userCodeText.text
            if(binding.userNameText.text != null || binding.userLastText.text != null){
                PrefsManager(requireContext()).deleteCode()
                if (code != null){
                    PrefsManager(requireContext()).saveCode(code.toString())
                }
                Toast.makeText(requireContext(), "Успешно", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_userSettingFragment_self)
            }

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