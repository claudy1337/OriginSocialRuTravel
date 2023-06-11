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
import com.autonture.originsocialrutravel.Utilis.Adapters.TownAdapter
import com.autonture.originsocialrutravel.Utilis.Classes.Town
import com.autonture.originsocialrutravel.Utilis.ConnectionService
import com.autonture.originsocialrutravel.Utilis.ThreadUtil.runOnUiThread
import com.autonture.originsocialrutravel.Utilis.ViewModels.TownViewModel
import com.autonture.originsocialrutravel.databinding.FragmentHomeTourBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeTourFragment : Fragment() {
    private lateinit var binding: FragmentHomeTourBinding
    private val viewModel: TownViewModel by viewModels()
    private lateinit var towns: List<Town>
    private lateinit var adapterTown: TownAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwipeRefreshLayout()
        setUpObservers()
        viewModel.getTowns()
    }

    private fun setUpObservers() {
        viewModel.towns.observe(viewLifecycleOwner) { towns ->
            this.towns = towns
            adapterTown = TownAdapter(requireContext(), towns as ArrayList<Town>)
            with(binding.cityList) {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = adapterTown
            }
        }

        viewModel.town.observe(viewLifecycleOwner) { town ->
            updatePost(town)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
    private fun initSwipeRefreshLayout() {
        binding.root.setOnRefreshListener {
            lifecycleScope.launch {
                delay(2000)
                CoroutineScope(Dispatchers.IO).launch {
                    val list = ConnectionService().service().getTowns()
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeTourBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}