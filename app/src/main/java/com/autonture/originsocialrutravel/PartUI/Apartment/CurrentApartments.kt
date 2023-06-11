package com.autonture.originsocialrutravel.PartUI.Apartment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.autonture.originsocialrutravel.databinding.FragmentCurrentApartmentsBinding


class CurrentApartments : Fragment() {
    private lateinit var binding: FragmentCurrentApartmentsBinding


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