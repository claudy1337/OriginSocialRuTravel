package com.autonture.originsocialrutravel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.autonture.originsocialrutravel.Utilis.Adapters.SearchCityAdapter
import com.autonture.originsocialrutravel.Utilis.ConnectionService
import com.autonture.originsocialrutravel.Utilis.ThreadUtil.runOnUiThread
import com.autonture.originsocialrutravel.databinding.FragmentSearchCityBinding
import com.autonture.originsocialrutravel.databinding.FragmentSettingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchCityFragment : Fragment() {
   private lateinit var binding: FragmentSearchCityBinding
    private lateinit var cityAdapter: SearchCityAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityAdapter = SearchCityAdapter()
        binding.apply {
            listCity.layoutManager = LinearLayoutManager(context)
            listCity.adapter = cityAdapter
        }
        CoroutineScope(Dispatchers.IO).launch {
            val list = ConnectionService().service().getTownsSearch()
            runOnUiThread {
                binding.apply {
                    cityAdapter.submitList(list)
                }
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchCityFragment()
    }
}