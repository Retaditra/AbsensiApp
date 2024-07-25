package com.absensi.app.jadwal

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
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.absensi.app.R
import com.absensi.app.data.Matkul
import com.absensi.app.data.database.JadwalEntity
import com.absensi.app.data.database.JadwalRepository
import com.absensi.app.databinding.FragmentJadwalBinding
import com.absensi.app.meet.MeetFragment
import com.absensi.app.utils.DataMapper
import com.absensi.app.utils.EncryptPreferences
import com.absensi.app.utils.expired
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JadwalFragment : Fragment() {

    private lateinit var binding: FragmentJadwalBinding
    private lateinit var adapter: JadwalAdapter
    private lateinit var repository: JadwalRepository
    private lateinit var recyclerView: RecyclerView
    private val handler = Handler(Looper.getMainLooper())
    private val viewModel: JadwalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJadwalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = JadwalRepository.getInstance(requireContext())
        recyclerView = binding.recyclerView

        setupRecyclerView()
        getSchedule()
        getJadwal()
        refresh()
    }

    private fun setupRecyclerView() {
        adapter = JadwalAdapter(
            onClick = {
                detail(it.id_mk.toString(), it.namaMatkul.toString())
            },
        )
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@JadwalFragment.adapter
        }
    }

    private fun listToAdapter(schedule: List<JadwalEntity>) {
        val data = DataMapper().entityToMeet(schedule)
        val pagingData: PagingData<Matkul> = PagingData.from(data)
        adapter.submitData(lifecycle, pagingData)
    }

    private fun getSchedule(callback: (Boolean, String?) -> Unit = { _, _ -> }) {
        val preference = EncryptPreferences(requireContext())
        val token = preference.getPreferences().getString("token", null)

        viewModel.getJadwal(token.toString(),
            onSuccess = {
                val pagingData: PagingData<Matkul> = PagingData.from(it)
                adapter.submitData(lifecycle, pagingData)
                recyclerView.layoutManager?.scrollToPosition(0)

                val data = mutableListOf<JadwalEntity>()
                for (schedule in it) {
                    val mapper = DataMapper().meetToEntity(schedule)
                    data.add(mapper)
                }
                lifecycleScope.launch {
                    repository.deleteAll()
                    repository.insertAll(data)
                }
                callback(true, null)
            },
            onFailure = {
                expired(it, requireContext())
                callback(false, it)
            },
            loading = {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            })
    }

    private fun getJadwal() {
        lifecycleScope.launch {
            val schedules = withContext(Dispatchers.IO) { repository.getJadwal() }
            listToAdapter(schedules)
        }
    }

    private fun refresh() {
        binding.refresh.setOnClickListener {
            binding.refresh.visibility = View.GONE
            getSchedule { success, message ->
                if (success) {
                    Toast.makeText(
                        requireContext(), getString(R.string.refreshSuccess), Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
            handler.postDelayed({
                binding.refresh.visibility = View.VISIBLE
            }, 3000.toLong())
        }
    }

    private fun detail(id: String, name: String) {
        val fragment = MeetFragment.newInstance(id, name)
        (requireActivity() as AppCompatActivity).supportFragmentManager
            .beginTransaction().apply {
                add(R.id.container_absent, fragment)
                addToBackStack(null)
                commit()
            }
    }
}