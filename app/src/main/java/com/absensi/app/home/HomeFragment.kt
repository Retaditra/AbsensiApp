package com.absensi.app.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.absensi.app.databinding.FragmentHomeBinding
import com.absensi.app.utils.EncryptPreferences
import com.absensi.app.utils.expired

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeAdapter
    private lateinit var recyclerView: RecyclerView
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

//        recyclerView = binding.rvHomeSchedule
//        adapter = HomeAdapter(
//            onClick = { showScheduleDetail(it) }
//        )
//        recyclerView.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = this@HomeFragment.adapter
//        }

        userProfile()
        //getTodaySch()
    }

    private fun userProfile() {
        val preference = EncryptPreferences(requireContext())
        val token = preference.getPreferences().getString("token", null)

        viewModel.userProfile(token.toString(),
            onSuccess = {
//                binding.apply {
//                    homeName.text = it.name
//                    homeRole.text = it.role
//                    Glide.with(requireContext())
//                        .load(it.imageProfile)
//                        .centerCrop()
//                        .into(binding.homeImage)
//                }
            },
            onFailure = {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                expired(it, requireContext())
            },
            loading = {
//                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            })
    }

//    private fun getTodaySch() {
//        val preference = EncryptPreferences(requireContext())
//        val token = preference.getPreferences().getString("token", null)
//
//        viewModel.getToday(token.toString(),
//            onSuccess = {
//                val pagingData: PagingData<Pertemuan> = PagingData.from(it)
//                adapter.submitData(lifecycle, pagingData)
//                recyclerView.layoutManager?.scrollToPosition(0)
//            },
//            onFailure = {
//                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//                expired(it, requireContext())
//            },
//            loading = {
//                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
//            })
//    }
}