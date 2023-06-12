package com.autonture.originsocialrutravel.PartUI.Apartment

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.autonture.originsocialrutravel.R
import com.autonture.originsocialrutravel.Utilis.Adapters.ApartmentAdapter
import com.autonture.originsocialrutravel.Utilis.Adapters.CommentAdapter
import com.autonture.originsocialrutravel.Utilis.Classes.*
import com.autonture.originsocialrutravel.Utilis.ConnectionService
import com.autonture.originsocialrutravel.Utilis.PrefsManager
import com.autonture.originsocialrutravel.Utilis.ViewModels.CommentViewModel
import com.autonture.originsocialrutravel.databinding.FragmentCurrentApartmentsBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CurrentApartments : Fragment() {
    private lateinit var binding: FragmentCurrentApartmentsBinding
    private lateinit var adapterComments: CommentAdapter
    private val viewModel: CommentViewModel by viewModels()
    private lateinit var comments: List<Comments>
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView15.setOnClickListener {
            val phoneNumber = binding.textView15.text.toString()
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phoneNumber")
            startActivity(intent)
        }
        val apartment = arguments?.getSerializable("apartment") as? Apartment
        apartment!!.id?.let { init(it) }

        setUpObservers()
        apartment.id?.let { viewModel.getComments(it) }


        val bitmap = apartment.Photos[0].photo?.let { decodeBase64ToBitmap(it) }
        binding.imgApartment.setImageBitmap(bitmap)
        binding.imgApartment2.setImageBitmap(bitmap)

        binding.btnSendComment.setOnClickListener {
            val currentDate = getCurrentDate()
            var user = PrefsManager(requireContext()).getUserId()
            if (binding.firstCommText.text != null || user!= null || apartment.id!= null){
                val comm = SendComment(binding.firstCommText.text.toString(), "${currentDate}", user, apartment.id, null, null)
                val call = ConnectionService().service().createComment(comm)
                call.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            // Запрос успешно выполнен
                            Toast.makeText(requireContext(), "Комментарий добавлен", Toast.LENGTH_SHORT).show()
                            val bundle = Bundle()
                            bundle.putSerializable("apartment", apartment)
                            findNavController().navigate(R.id.action_currentApartments_self, bundle)
                            setUpObservers()
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

    private fun setUpObservers() {
        viewModel.comments.observe(viewLifecycleOwner) { comments ->
            this.comments = comments
            adapterComments = CommentAdapter(requireContext(), comments as ArrayList<Comments>)
            with(binding.commentsList) {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = adapterComments
            }
        }

        viewModel.comm.observe(viewLifecycleOwner) { comm ->
            updatePost(comm)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updatePost(post: Comments) {
        adapterComments.updatePost(post)
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
                                    binding.pice.text = "Цена: ${apartment.pricePerMonth}р"
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