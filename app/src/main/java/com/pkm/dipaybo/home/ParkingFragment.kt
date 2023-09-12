package com.pkm.dipaybo.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pkm.dipaybo.R
import com.pkm.dipaybo.databinding.FragmentParkingBinding


class ParkingFragment : Fragment() {

    private var _binding: FragmentParkingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentParkingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView(){
        val sectionPagerAdapter = SectionPagerAdapterParking(requireActivity() as AppCompatActivity)
        val parkingViewPager: ViewPager2? = view?.findViewById(R.id.view_pager_parking)
        parkingViewPager?.adapter = sectionPagerAdapter
        val tabsParking: TabLayout? = view?.findViewById(R.id.tabl_parking)
        if (tabsParking != null) {
            if (parkingViewPager != null) {
                TabLayoutMediator(tabsParking, parkingViewPager) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
            }
        }
    }
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.ongoing,
            R.string.completed
        )
    }
}