package com.autonture.originsocialrutravel.PartUI.Authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.autonture.originsocialrutravel.MAIN
import com.autonture.originsocialrutravel.R
import com.autonture.originsocialrutravel.databinding.FragmentForgetProfileBinding


class ForgetProfileFragment : Fragment() {
    private lateinit var binding: FragmentForgetProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgetProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.authAccTxt.setOnClickListener {
            MAIN.navController.navigate(R.id.action_forgetProfileFragment_to_signIn)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ForgetProfileFragment()
    }
}