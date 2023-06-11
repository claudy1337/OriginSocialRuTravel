package com.autonture.originsocialrutravel.PartUI.Apartment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.autonture.originsocialrutravel.databinding.FragmentRoomSearchBinding


class RoomSearchFragment : Fragment() {
    private lateinit var binding: FragmentRoomSearchBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRoomSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}
