package com.autonture.originsocialrutravel.PartUI.Messenger

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.autonture.originsocialrutravel.MAIN
import com.autonture.originsocialrutravel.R
import com.autonture.originsocialrutravel.Utilis.PrefsManager
import com.autonture.originsocialrutravel.databinding.FragmentAnonymousMessengerBinding


class AnonymousMessengerFragment : Fragment() {
    private lateinit var binding: FragmentAnonymousMessengerBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnonymousMessengerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var isLogin = PrefsManager(requireContext()).isLogged()
        if(isLogin){
            MAIN.navController.navigate(R.id.action_anonymousMessengerFragment_to_userMessengerFragment2)
        }
        else{
            MAIN.navController.navigate(R.id.action_anonymousMessengerFragment_to_signIn3)
        }
    }
}