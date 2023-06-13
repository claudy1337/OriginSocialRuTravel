package com.autonture.originsocialrutravel.PartUI.Apartment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.autonture.originsocialrutravel.MAIN
import com.autonture.originsocialrutravel.R
import com.autonture.originsocialrutravel.Utilis.Adapters.ApartmentAdapter
import com.autonture.originsocialrutravel.Utilis.Classes.Apartment
import com.autonture.originsocialrutravel.Utilis.ViewModels.ApartmentViewModel
import com.autonture.originsocialrutravel.Utilis.ViewModels.TownViewModel
import com.autonture.originsocialrutravel.databinding.FragmentRoomSearchBinding


class RoomSearchFragment : Fragment() {
    private lateinit var binding: FragmentRoomSearchBinding
    private lateinit var apartments: List<Apartment>
    private val viewModelApartment: ApartmentViewModel by viewModels()
    private lateinit var adapterApartment: ApartmentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        viewModelApartment.getTowns()
    }
    private fun setUpObservers() {
        viewModelApartment.posts.observe(viewLifecycleOwner) { posts ->
            this.apartments = posts
            adapterApartment = ApartmentAdapter(requireContext(), posts as ArrayList<Apartment>, onClick = {
                val bundle = Bundle()
                bundle.putSerializable("apartment", it)
                findNavController().navigate(R.id.action_searchApartments_to_currentApartments, bundle)
            })
            with(binding.apartmentList) {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = adapterApartment
            }
        }

        viewModelApartment.post.observe(viewLifecycleOwner) { post ->
            updateApartment(post)
        }

        viewModelApartment.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateApartment(post: Apartment) {
        adapterApartment.updatePost(post)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRoomSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}
