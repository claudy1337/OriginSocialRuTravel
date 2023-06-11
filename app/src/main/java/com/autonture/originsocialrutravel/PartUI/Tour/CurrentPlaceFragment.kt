package com.autonture.originsocialrutravel.PartUI.Tour

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.autonture.originsocialrutravel.databinding.FragmentCurrentPlaceBinding

class CurrentPlaceFragment : Fragment() {
    private lateinit var binding: FragmentCurrentPlaceBinding


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