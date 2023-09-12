package com.pkm.dipaybo.home

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapterParking(activity: AppCompatActivity): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> {
                fragment = OngoingParkingFragment()
            }
            1 -> {
                fragment = CompletedParkingFragment()
            }
        }
        return fragment as Fragment
    }
}