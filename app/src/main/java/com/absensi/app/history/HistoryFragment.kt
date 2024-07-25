package com.absensi.app.history

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
import com.absensi.app.R
import com.absensi.app.data.Pertemuan
import com.absensi.app.databinding.FragmentHistoryBinding
import com.absensi.app.utils.EncryptPreferences
import com.absensi.app.utils.expired

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: HistoryAdapter
    private lateinit var recyclerView: RecyclerView
    private val handler = Handler(Looper.getMainLooper())
    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvHistory
        adapter = HistoryAdapter(
            context = requireContext(),
            absent = { Toast.makeText(requireContext(), it.id, Toast.LENGTH_SHORT).show() })

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HistoryFragment.adapter
        }

        getHistory()
        refresh()
    }

    private fun getHistory(callback: (Boolean, String?) -> Unit = { _, _ -> }) {
        val preference = EncryptPreferences(requireContext())
        val token = preference.getPreferences().getString("token", null)

        viewModel.getHistory(token.toString(),
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
            getHistory { success, message ->
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
}