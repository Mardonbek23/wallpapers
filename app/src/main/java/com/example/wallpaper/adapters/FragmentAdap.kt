package com.example.wallpaper.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.wallpaper.fragments.ViewFragment

class FragmentAdap(fragmentActivity: FragmentManager,var list: ArrayList<String>,var order_by:String) : FragmentPagerAdapter(fragmentActivity) {

    val listFragment = ArrayList<ViewFragment>()

    override fun getItem(position: Int): Fragment {
        val newInstance = ViewFragment.newInstance(list[position],order_by)
        listFragment.add(newInstance)
        return newInstance
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list[position]
    }
}