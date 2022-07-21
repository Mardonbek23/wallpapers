package com.example.wallpaper.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import com.example.wallpaper.R
import com.example.wallpaper.databinding.FragmentHeadBinding
import com.example.wallpaper.fragments.ui.*
import com.google.android.material.navigation.NavigationView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HeadFragment : Fragment() {
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

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding: FragmentHeadBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHeadBinding.inflate(inflater, container, false)
        getFragment()
        setDrawer()
        setOptions()
        return binding.root
    }

    private fun setOptions() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_random -> {
                    binding.toolbar.menu.clear()
                    binding.toolbar.inflateMenu(R.menu.random_menu)
                    binding.toolbar.setTitle("Random")
                    var fragmentManager = getFragmentManager()
                    var name = fragmentManager!!.beginTransaction()
                    name.replace(R.id.fragment, RandomFragment())
                    name.commit()


                }
                R.id.search -> {
                    Navigation.findNavController(binding.root).navigate(R.id.searchFragment)
                }
            }
            true
        }
    }

    private fun setDrawer() {
        toggle = ActionBarDrawerToggle(
            requireActivity(),
            binding.drawer,
            binding.toolbar,
            R.string.open,
            R.string.close
        )
        toggle.drawerArrowDrawable.color = Color.WHITE
        binding.drawer.addDrawerListener(toggle)
        binding.toolbar.inflateMenu(R.menu.search_menu)
        toggle.syncState()
        binding.navmenu.setNavigationItemSelectedListener { item ->
            binding.toolbar.menu.clear()
            when (item.itemId) {
                R.id.menu_home -> {
                    binding.toolbar.inflateMenu(R.menu.search_menu)
                    binding.toolbar.setTitle("Home")
                    var fragmentManager = getFragmentManager()
                    var name = fragmentManager!!.beginTransaction()
                    name.replace(R.id.fragment, HomeFragment())
                    name.commit()
                }
                R.id.menu_popular -> {
                    binding.toolbar.inflateMenu(R.menu.search_menu)
                    binding.toolbar.setTitle("Popular")
                    var fragmentManager = getFragmentManager()
                    var name = fragmentManager!!.beginTransaction()
                    name.replace(R.id.fragment, PopularFragment())
                    name.commit()
                }
                R.id.menu_random -> {
                    binding.toolbar.inflateMenu(R.menu.random_menu)
                    binding.toolbar.setTitle("Random")
                    var fragmentManager = getFragmentManager()
                    var name = fragmentManager!!.beginTransaction()
                    name.replace(R.id.fragment, RandomFragment())
                    name.commit()
                }
                R.id.menu_liked -> {
                    binding.toolbar.setTitle("My Favourites")
                    var fragmentManager = getFragmentManager()
                    var name = fragmentManager!!.beginTransaction()
                    name.replace(R.id.fragment, LikedFragment())
                    name.commit()
                }
                R.id.menu_history -> {
                    binding.toolbar.setTitle("History")
                    var fragmentManager = getFragmentManager()
                    var name = fragmentManager!!.beginTransaction()
                    name.replace(R.id.fragment, HistoryFragment())
                    name.commit()
                }


            }
            binding.drawer.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun getFragment() {
        binding.navmenu.itemIconTintList = null
        var fragmentManager = getFragmentManager()
        var name = fragmentManager!!.beginTransaction()
        name.add(R.id.fragment, HomeFragment())
        name.commit()
    }


}