package com.autonture.originsocialrutravel.PartUI.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.autonture.originsocialrutravel.MAIN
import com.autonture.originsocialrutravel.R
import com.autonture.originsocialrutravel.Utilis.PrefsManager
import com.autonture.originsocialrutravel.databinding.FragmentAnonymousProfileBinding

class AnonymousProfileFragment : Fragment() {
    private lateinit var binding: FragmentAnonymousProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnonymousProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var isLogin = PrefsManager(requireContext()).isLogged()
        if(isLogin){
            MAIN.navController.navigate(R.id.action_anonymousProfileFragment_to_userProfileFragment2)
        }
        else{
            MAIN.navController.navigate(R.id.action_anonymousProfileFragment_to_signIn2)
        }
    }
}