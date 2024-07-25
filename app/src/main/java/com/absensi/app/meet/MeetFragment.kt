package com.absensi.app.meet

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.absensi.app.R
import com.absensi.app.absent.AbsentFragment
import com.absensi.app.data.Pertemuan
import com.absensi.app.databinding.FragmentDetailBinding
import com.absensi.app.utils.EncryptPreferences
import com.absensi.app.utils.expired
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MeetFragment : BottomSheetDialogFragment() {

    private lateinit var preference: EncryptPreferences
    private lateinit var binding: FragmentDetailBinding
    private lateinit var adapter: MeetAdapter
    private lateinit var recyclerView: RecyclerView
    private val handler = Handler(Looper.getMainLooper())
    private val viewModel: MeetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        preference = EncryptPreferences(requireContext())

        binding.namaMk.text = arguments?.getString("name")

        recyclerView = binding.rvDetail

        adapter = MeetAdapter(
            context = requireContext(),
            absent = { absent(it.id, it.namaMatkul.toString(), it.pertemuan_ke.toString()) })

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@MeetFragment.adapter
        }

        setupButton()
        setupView()

        return binding.root
    }

    private fun setupButton() {
        binding.background.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.closeButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun setupView() {
        binding.namaMk.text = arguments?.getString("name").toString()
        val id = arguments?.getString("id").toString()

        getMeet(id)
        refresh(id)
    }

    private fun getMeet(id: String, callback: (Boolean, String?) -> Unit = { _, _ -> }) {
        val preference = EncryptPreferences(requireContext())
        val token = preference.getPreferences().getString("token", null)

        viewModel.getMeet(token.toString(), id,
            onSuccess = {
                if (isAdded) {
                    if (it.isEmpty()) {
                        binding.noData.visibility = View.VISIBLE
                    } else {
                        val pagingData: PagingData<Pertemuan> = PagingData.from(it)
                        adapter.submitData(lifecycle, pagingData)
                        recyclerView.layoutManager?.scrollToPosition(0)
                        callback(true, null)
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

    private fun absent(id: String, name: String, ptm: String) {
        val fragment = AbsentFragment.newInstance(id, name, ptm)
        (requireActivity() as AppCompatActivity).supportFragmentManager
            .beginTransaction().apply {
                replace(R.id.fragment_container, fragment)
                addToBackStack(null)
                commit()
            }
    }

    private fun refresh(id: String) {
        binding.refresh.setOnClickListener {
            binding.refresh.visibility = View.GONE
            getMeet(id) { success, message ->
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

    companion object {
        fun newInstance(id: String, name: String): MeetFragment {
            val fragment = MeetFragment()
            val args = Bundle()
            args.putString("id", id)
            args.putString("name", name)
            fragment.arguments = args
            return fragment
        }
    }
}