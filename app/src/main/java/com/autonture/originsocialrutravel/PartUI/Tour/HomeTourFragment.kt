package com.autonture.originsocialrutravel.PartUI.Tour

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.autonture.originsocialrutravel.Utilis.Adapters.PlaceAdapter
import com.autonture.originsocialrutravel.Utilis.Adapters.TownAdapter
import com.autonture.originsocialrutravel.Utilis.Classes.Place
import com.autonture.originsocialrutravel.Utilis.Classes.Town
import com.autonture.originsocialrutravel.Utilis.ConnectionService
import com.autonture.originsocialrutravel.Utilis.ThreadUtil.runOnUiThread
import com.autonture.originsocialrutravel.Utilis.ViewModels.PlaceViewModel
import com.autonture.originsocialrutravel.Utilis.ViewModels.TownViewModel
import com.autonture.originsocialrutravel.databinding.FragmentHomeTourBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeTourFragment : Fragment() {
    private lateinit var binding: FragmentHomeTourBinding
    private val viewModelTown: TownViewModel by viewModels()
    private val viewModelPlace: PlaceViewModel by viewModels()

    private lateinit var adapterTown: TownAdapter
    private lateinit var adapterPlace: PlaceAdapter

    private lateinit var towns: List<Town>
    private lateinit var places: List<Place>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwipeRefreshLayout()
        setUpObservers()
        setUpPlaceObservers()
        viewModelTown.getTowns()
        viewModelPlace.getPlaces()
    }

    private fun setUpObservers() {
        viewModelTown.towns.observe(viewLifecycleOwner) { towns ->
            this.towns = towns
            adapterTown = TownAdapter(requireContext(), towns as ArrayList<Town>)
            with(binding.cityList) {
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                adapter = adapterTown
            }
        }

        viewModelTown.town.observe(viewLifecycleOwner) { town ->
            updatePost(town)
        }

        viewModelTown.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
    private fun setUpPlaceObservers(){
        viewModelPlace.places.observe(viewLifecycleOwner) {places->
            this.places = places
            adapterPlace = PlaceAdapter(requireContext(), places as ArrayList<Place>)
            with(binding.placeList){
                layoutManager = LinearLayoutManager(requireContext(),  RecyclerView.HORIZONTAL, false)
                adapter = adapterPlace
            }
        }
        viewModelPlace.place.observe(viewLifecycleOwner) { place ->
            updatePlace(place)
        }

        viewModelPlace.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initSwipeRefreshLayout() {
        binding.root.setOnRefreshListener {
            lifecycleScope.launch {
                delay(2000)
                CoroutineScope(Dispatchers.IO).launch {
                    val list = ConnectionService().service().getTowns()
                    val listPlace = ConnectionService().service().getPlaces()
                    runOnUiThread {

                    }
                }
                binding.root.isRefreshing = false
            }
        }
    }
    private fun updatePost(post: Town) {
        adapterTown.updatePost(post)
    }
    private fun updatePlace(place: Place){
        adapterPlace.updatePost(place)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeTourBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}