package com.autonture.originsocialrutravel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.autonture.originsocialrutravel.databinding.FragmentCurrentCityBinding


class CurrentCityFragment : Fragment() {
   private lateinit var binding: FragmentCurrentCityBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrentCityBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = CurrentCityFragment()
    }
}