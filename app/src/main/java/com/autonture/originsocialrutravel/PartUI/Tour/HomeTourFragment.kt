package com.autonture.originsocialrutravel.PartUI.Tour

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.autonture.originsocialrutravel.databinding.FragmentHomeTourBinding


class HomeTourFragment : Fragment() {
    private lateinit var binding: FragmentHomeTourBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeTourBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}