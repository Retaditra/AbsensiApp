package com.absensi.app.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.absensi.app.R
import com.absensi.app.databinding.ActivityMainBinding
import com.absensi.app.login.LoginActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.absensi.app.utils.EncryptPreferences


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //checkTokenAndNavigate()

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        tabLayoutView()
        backPressed()
    }

    private fun tabLayoutView() {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    //tab.text = "Home"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.home)
                }
                1 -> {
                    //tab.text = "Jadwal"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.task)
                }
                2 -> {
                    //tab.text = "History"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.history)
                }
                3 -> {
                    //tab.text = "Profile"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.user)
                }
            }
        }.attach()
    }

    private fun backPressed() {
        var isBackPressedOnce = false
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isBackPressedOnce) {
                    finish()
                } else {
                    isBackPressedOnce = true
                    Toast.makeText(
                        this@MainActivity, getString(R.string.closeWarning),
                        Toast.LENGTH_SHORT
                    ).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        isBackPressedOnce = false
                    }, 2000)
                }
            }
        })
    }

    private fun checkTokenAndNavigate() {
        val preference = EncryptPreferences(applicationContext)
        val token = preference.getPreferences().getString("token", null)

        if (token == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}