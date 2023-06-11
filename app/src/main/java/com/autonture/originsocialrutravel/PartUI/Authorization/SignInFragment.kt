package com.autonture.originsocialrutravel.PartUI.Authorization

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.recreate
import com.autonture.originsocialrutravel.MAIN
import com.autonture.originsocialrutravel.MainActivity
import com.autonture.originsocialrutravel.R
import com.autonture.originsocialrutravel.Utilis.PrefsManager
import com.autonture.originsocialrutravel.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtRegistration.setOnClickListener {
            MAIN.navController.navigate(R.id.action_signIn_to_registrationFragment)
        }
        binding.txtForgetPass.setOnClickListener {
            MAIN.navController.navigate(R.id.action_signIn_to_forgetProfileFragment)
        }
        binding.authBtn.setOnClickListener {
            PrefsManager(requireContext()).setLogged(true)
            recreate(requireActivity())

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignInFragment()
    }
}