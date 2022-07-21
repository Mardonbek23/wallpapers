package com.example.wallpaper.fragments.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wallpaper.R
import com.example.wallpaper.adapters.FragmentAdap
import com.example.wallpaper.databinding.FragmentHomeBinding
import com.example.wallpaper.databinding.ItemTabBinding
import com.google.android.material.tabs.TabLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentHomeBinding
    lateinit var fragmentAdap: FragmentAdap
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setTabs()
        setHasOptionsMenu(false)
        return binding.root
    }

    private fun setTabs() {
        val list = ArrayList<String>()
        list.add("ALL")
        list.add("NEW")
        list.add("ANIMALS")
        list.add("TECHNOLOGY")
        list.add("NATURE")
        fragmentAdap = FragmentAdap(childFragmentManager, list,"relevant")
        binding.viewPager.adapter = fragmentAdap
        binding.tabs.setupWithViewPager(binding.viewPager, true)
        for (i in 0 until list.size) {
            val tabAt: TabLayout.Tab? = binding.tabs.getTabAt(i)
            val tabbinding = ItemTabBinding.inflate(layoutInflater)
            tabbinding.text.setText(list[i])
            if (i == 0) {
                tabbinding.text.alpha = 1.0f
                tabbinding.circle.visibility = View.VISIBLE
            }
            tabAt?.setCustomView(tabbinding.root)
        }
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val customView = tab.customView
                val itemTabBinding1 = ItemTabBinding.bind(customView!!)
                itemTabBinding1.circle.visibility = View.VISIBLE
                itemTabBinding1.text.alpha = 1.0f
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val customView = tab.customView
                val itemTabBinding1 = ItemTabBinding.bind(customView!!)
                itemTabBinding1.circle.visibility = View.INVISIBLE
                itemTabBinding1.text.alpha = 0.5f
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }


}