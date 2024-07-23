package com.absensi.app.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.absensi.app.data.Pertemuan
import com.absensi.app.databinding.FragmentHomeBinding
import com.absensi.app.utils.EncryptPreferences
import com.absensi.app.utils.expired
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeAdapter
    private lateinit var recyclerView: RecyclerView
    private val handler = Handler(Looper.getMainLooper())
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvHome
        adapter = HomeAdapter(
            context = requireContext(),
            absent = { Toast.makeText(requireContext(), it.id, Toast.LENGTH_SHORT).show() })

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HomeFragment.adapter
        }

        setupDate()
        userProfile()
        getToday()
    }

    private fun setupDate() {
        updateTime()

        handler.post(object : Runnable {
            override fun run() {
                updateTime()
                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun updateTime() {
        val currentTime = Calendar.getInstance().time
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        binding.time.text = timeFormat.format(currentTime)

        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        binding.date.text = dateFormat.format(currentTime)

        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        binding.day.text = dayFormat.format(currentTime)
    }

    private fun userProfile() {
        val preference = EncryptPreferences(requireContext())
        val token = preference.getPreferences().getString("token", null)

        viewModel.userProfile(token.toString(),
            onSuccess = {
                binding.apply {
                    name.text = it.nama
                    nim.text = it.nim
                }
            },
            onFailure = {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                expired(it, requireContext())
            },
            loading = {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            })
    }

    private fun getToday() {
        val preference = EncryptPreferences(requireContext())
        val token = preference.getPreferences().getString("token", null)

        viewModel.getToday(token.toString(),
            onSuccess = {
                val pagingData: PagingData<Pertemuan> = PagingData.from(it)
                adapter.submitData(lifecycle, pagingData)
                recyclerView.layoutManager?.scrollToPosition(0)
            },
            onFailure = {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                expired(it, requireContext())
            },
            loading = {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            })
    }
}