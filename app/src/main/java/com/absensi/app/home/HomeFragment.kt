package com.absensi.app.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.absensi.app.R
import com.absensi.app.absent.AbsentFragment
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
            absent = { absent(it.id, it.namaMatkul.toString(), it.pertemuan_ke.toString()) })

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HomeFragment.adapter
        }

        setupDate()
        userProfile()
        getToday()
        refresh()
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
                if (isAdded) {
                    binding.apply {
                        name.text = it.nama
                        nim.text = it.nim
                    }
                }
            },
            onFailure = {
                if (isAdded) {
                    expired(it, requireContext())
                }
            },
            loading = {
                if (isAdded) {
                    binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
                }
            })
    }

    private fun getToday(callback: (Boolean, String?) -> Unit = { _, _ -> }) {
        val preference = EncryptPreferences(requireContext())
        val token = preference.getPreferences().getString("token", null)

        viewModel.getToday(token.toString(),
            onSuccess = {
                if (isAdded) {
                    val pagingData: PagingData<Pertemuan> = PagingData.from(it)
                    adapter.submitData(lifecycle, pagingData)
                    recyclerView.layoutManager?.scrollToPosition(0)
                    callback(true, null)
                    if (it.isEmpty()) {
                        binding.noData.visibility = View.VISIBLE
                    }
                }
            },

            onFailure = {
                if (isAdded) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    expired(it, requireContext())
                    callback(false, it)
                }
            },
            loading = {
                if (isAdded) {
                    binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
                }
            })
    }

    private fun refresh() {
        binding.refresh.setOnClickListener {
            binding.refresh.visibility = View.GONE
            getToday { success, message ->
                if (isAdded) {
                    if (success) {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.refreshSuccess),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            handler.postDelayed({
                if (isAdded) {
                    binding.refresh.visibility = View.VISIBLE
                }
            }, 3000.toLong())
        }
    }

    private fun absent(id: String, name: String, ptm: String) {
        val fragment = AbsentFragment.newInstance(id, name, ptm)
        (requireActivity() as AppCompatActivity).supportFragmentManager
            .beginTransaction().apply {
                replace(R.id.fragment_container, fragment)
                addToBackStack(null)
                commit()
            }
    }
}