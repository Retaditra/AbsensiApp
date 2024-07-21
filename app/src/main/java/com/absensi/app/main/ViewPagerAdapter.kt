package com.absensi.app.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.absensi.app.history.HistoryFragment
import com.absensi.app.home.HomeFragment
import com.absensi.app.jadwal.JadwalFragment
import com.absensi.app.profile.ProfileFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> JadwalFragment()
            2 -> HistoryFragment()
            3 -> ProfileFragment()
            else -> HomeFragment()
        }
    }
}
