package com.autonture.originsocialrutravel.PartUI.Apartment

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.autonture.originsocialrutravel.R
import com.autonture.originsocialrutravel.Utilis.Classes.Apartment
import com.autonture.originsocialrutravel.Utilis.Classes.Photo
import com.autonture.originsocialrutravel.Utilis.Classes.Town
import com.autonture.originsocialrutravel.Utilis.Classes.User
import com.autonture.originsocialrutravel.Utilis.ConnectionService
import com.autonture.originsocialrutravel.Utilis.PrefsManager
import com.autonture.originsocialrutravel.databinding.FragmentCurrentApartmentsBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CurrentApartments : Fragment() {
    private lateinit var binding: FragmentCurrentApartmentsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView15.setOnClickListener {
            val phoneNumber = binding.textView15.text.toString()
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phoneNumber")
            startActivity(intent)
        }
        init(4)
    }
    private fun init(id:Int){
        val getApartmentCall = ConnectionService().service().getApartment(id)
        getApartmentCall.enqueue(object : Callback<Apartment> {
            override fun onResponse(call: Call<Apartment>, response: Response<Apartment>) {
                if (response.isSuccessful) {
                    val apartment = response.body()
                    if (apartment != null) {
                        val getApartmentPhoto =
                            apartment.id?.let {ConnectionService().service().getPhotoApartmentId(it) }
                        getApartmentPhoto?.enqueue(object : Callback<Photo> {
                            override fun onResponse(call: Call<Photo>, response: Response<Photo>) {
                                if (response.isSuccessful) {
                                    val photo = response.body()
                                    if (photo != null) {
                                        binding.imgApartment.setImageBitmap(decodeBase64ToBitmap("${photo.photo}"))
                                    }
                                } else {
                                    // Обработка ошибки получения адреса
                                }
                            }

                            override fun onFailure(call: Call<Photo>, t: Throwable) {

                            }
                        })

                        val getAddressCall =
                            apartment.townsRefID?.let { ConnectionService().service().getTownsId(it) }
                        getAddressCall?.enqueue(object : Callback<Town> {
                            override fun onResponse(call: Call<Town>, response: Response<Town>) {
                                if (response.isSuccessful) {
                                    val town = response.body()
                                    if (town != null) {
                                        // Получение адреса успешно, отобразите данные в TextView
                                        binding.txtCity.text = town.name

                                    }
                                    binding.txtAddress.text = apartment.address
                                    binding.textView15.text = "Номер: ${apartment.ownersPhone}"
                                    binding.textView10.text = "Этаж: ${apartment.floor}"
                                    binding.textView9.text = "Комнат: ${apartment.countOfRooms}"
                                    binding.ratingText.text = "${apartment.rating}"
                                } else {
                                    // Обработка ошибки получения адреса
                                }
                            }

                            override fun onFailure(call: Call<Town>, t: Throwable) {
                                // Обработка ошибки сети при получении адреса
                            }
                        })
                    }
                } else {
                    // Обработка ошибки получения пользователя
                }
            }
            override fun onFailure(call: Call<Apartment>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun decodeBase64ToBitmap(base64String: String): Bitmap {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrentApartmentsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = CurrentApartments()
    }
}