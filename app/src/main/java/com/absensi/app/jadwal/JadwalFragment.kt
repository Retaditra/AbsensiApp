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

        getSchedule()
        setupRecyclerViews()
        getJadwal()
        refresh()
    }

    private fun setupRecyclerViews() {
        adapter = JadwalAdapter(
            onClick = {
                detail(it.id_mk.toString(), it.namaMatkul.toString())
            },
        )
        val recyclerViews = listOf(
            binding.rvSenin,
            binding.rvSelasa,
            binding.rvRabu,
            binding.rvKamis,
            binding.rvJumat
        )
        recyclerViews.forEach { recyclerView ->
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        }
    }

    private fun getJadwal() {
        val days = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat")
        val recyclerViews = listOf(
            binding.rvSenin,
            binding.rvSelasa,
            binding.rvRabu,
            binding.rvKamis,
            binding.rvJumat
        )

        days.forEachIndexed { index, day ->
            lifecycleScope.launch {
                val dayData = withContext(Dispatchers.IO) { repository.getByDay(day) }
                val data = DataMapper().entityToMeet(dayData)
                val pagingData: PagingData<Matkul> = PagingData.from(data)
                recyclerViews[index].adapter = JadwalAdapter(
                    onClick = {
                        detail(it.id_mk.toString(), it.namaMatkul.toString())
                    }
                ).apply {
                    submitData(lifecycle, pagingData)
                }
            }
        }
    }

    private fun getSchedule(callback: (Boolean, String?) -> Unit = { _, _ -> }) {
        val preference = EncryptPreferences(requireContext())
        val token = preference.getPreferences().getString("token", null)

        viewModel.getJadwal(token.toString(),
            onSuccess = {
                if (isAdded) {
                    val data = mutableListOf<JadwalEntity>()
                    for (schedule in it) {
                        val mapper = DataMapper().meetToEntity(schedule)
                        data.add(mapper)
                    }
                    lifecycleScope.launch {
                        if (data.isNotEmpty()) {
                            repository.deleteAll()
                            repository.insertAll(data)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Tidak Ada Jadwal Minggu Ini",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    callback(true, null)
                }
            },
            onFailure = {
                if (isAdded) {
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
            setupRecyclerViews()
            getJadwal()
            getSchedule { success, message ->
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