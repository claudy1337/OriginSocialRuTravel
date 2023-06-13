package com.autonture.originsocialrutravel.PartUI.Tour

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.autonture.originsocialrutravel.Utilis.Classes.Town
import com.autonture.originsocialrutravel.databinding.FragmentCurrentCityBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject

const val API_KEY = "35e5aac3ab4143e9952204811232005"
class CurrentCityFragment : Fragment() {
   private lateinit var binding: FragmentCurrentCityBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val city = arguments?.getSerializable("city") as? Town

        city!!.id?.let { init(it, city) }
        city.name?.let { getTemp(it) }

    }
    private fun init(id:Int, city:Town){
        if (city!=null){
            binding.nameCity.text = city.name
            binding.textView4.text = city.description
            binding.textView21.text = city.rating.toString()
            val bitmap = city.Photos[0].photo?.let { decodeBase64ToBitmap(it) }
            binding.imgCity.setImageBitmap(bitmap)
        }
    }
    fun decodeBase64ToBitmap(base64String: String): Bitmap {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrentCityBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    fun getTemp(name:String){
        val url = "http://api.weatherapi.com/v1/current.json" +
                "?key=$API_KEY&q=$name&aqi=no"
        val queue = Volley.newRequestQueue(requireContext())
        val stringRequest = StringRequest(
            Request.Method.GET,
            url, {
                    responce ->
                val obj = JSONObject(responce)
                val temp = obj.getJSONObject("current")
                binding.tempC.setText(temp.getString("temp_c"))

            },
            {

            }
        )
        queue.add(stringRequest)
    }

    companion object {

        @JvmStatic
        fun newInstance() = CurrentCityFragment()
    }
}