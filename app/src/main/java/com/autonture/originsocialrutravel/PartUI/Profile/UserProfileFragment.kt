package com.autonture.originsocialrutravel.PartUI.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.autonture.originsocialrutravel.R
import com.autonture.originsocialrutravel.databinding.FragmentUserProfileBinding


class UserProfileFragment : Fragment() {
    private lateinit var binding:FragmentUserProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = UserProfileFragment()
    }
}