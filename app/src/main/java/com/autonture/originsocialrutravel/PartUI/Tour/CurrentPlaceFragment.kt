package com.autonture.originsocialrutravel.PartUI.Tour

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.autonture.originsocialrutravel.R
import com.autonture.originsocialrutravel.Utilis.Adapters.CommentAdapter
import com.autonture.originsocialrutravel.Utilis.Classes.*
import com.autonture.originsocialrutravel.Utilis.ConnectionService
import com.autonture.originsocialrutravel.Utilis.PrefsManager
import com.autonture.originsocialrutravel.Utilis.ViewModels.CommentPlaceViewModel
import com.autonture.originsocialrutravel.Utilis.ViewModels.CommentViewModel
import com.autonture.originsocialrutravel.databinding.FragmentCurrentPlaceBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CurrentPlaceFragment : Fragment() {
    private lateinit var binding: FragmentCurrentPlaceBinding
    private lateinit var placeComments: CommentAdapter
    private val viewModel: CommentPlaceViewModel by viewModels()
    private lateinit var comments: List<Comments>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val place = arguments?.getSerializable("place") as? Place
        place?.let { initPlace(it) }
        setUpObservers()
        place?.id?.let { viewModel.getComments(it) }

        binding.btnSendComment.setOnClickListener {
        val currentDate = getCurrentDate()
        var user = PrefsManager(requireContext()).getUserId()
        if (binding.firstCommText.text != null || user!= null || place?.id != null){
            val comm = SendComment(binding.firstCommText.text.toString(), "${currentDate}", user, null, null, place?.id)
            val call = ConnectionService().service().createComment(comm)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        // Запрос успешно выполнен
                        Toast.makeText(requireContext(), "Комментарий добавлен", Toast.LENGTH_SHORT).show()
                        val bundle = Bundle()
                        bundle.putSerializable("place", place)
                        findNavController().navigate(R.id.action_currentPlaceFragment_self, bundle)
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
        else{
            Toast.makeText(requireContext(), "Заполните поля", Toast.LENGTH_SHORT).show()
        }
    }
    }
    fun getCurrentDate(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(currentDate)
    }
    private fun updatePost(post: Comments) {
        placeComments.updatePost(post)
    }
    private fun setUpObservers() {
        viewModel.comments.observe(viewLifecycleOwner) { comments ->
            this.comments = comments
            placeComments = CommentAdapter(requireContext(), comments as ArrayList<Comments>)
            with(binding.commentsList) {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = placeComments
            }
        }

        viewModel.comm.observe(viewLifecycleOwner) { comm ->
            updatePost(comm)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    fun initPlace(place:Place){
            if (place!=null){
                binding.placeName.text = place!!.title
                binding.textView10.text = place!!.address
                binding.placeRating.text = place.rating.toString()
                place.townsRefID?.let { getPlace(it) }
                val bitmap = place.Photos[0].photo?.let { decodeBase64ToBitmap(it) }
                binding.placeImage.setImageBitmap(bitmap)
                binding.imagePlace2.setImageBitmap(bitmap)
            }
    }

    private fun getPlace(townsRefID:Int){
        val call = ConnectionService().service().getTownId(townsRefID)
        call.enqueue(object : Callback<Town> {
            override fun onResponse(call: Call<Town>, response: Response<Town>) {
                if (response.isSuccessful) {
                    val town = response.body()
                    // Обработка успешного ответа
                    if (town!= null){
                        binding.placeCity.text = town.name
                    }
                } else {
                    // Обработка ошибки
                }
            }
            override fun onFailure(call: Call<Town>, t: Throwable) {
                // Обработка ошибки
            }
        })
    }

    fun decodeBase64ToBitmap(base64String: String): Bitmap {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrentPlaceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CurrentPlaceFragment()
    }
}