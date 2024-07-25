package com.absensi.app.profile

import android.content.Intent
import android.os.Bundle
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
import com.absensi.app.data.database.JadwalRepository
import com.absensi.app.databinding.FragmentProfileBinding
import com.absensi.app.login.LoginActivity
import com.absensi.app.meet.MeetFragment
import com.absensi.app.utils.EncryptPreferences
import com.absensi.app.utils.UserProfilePreferences
import com.absensi.app.utils.expired
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var preference: EncryptPreferences
    private lateinit var adapter: ProfileAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preference = EncryptPreferences(requireContext())

        recyclerView = binding.rvProfile
        adapter = ProfileAdapter(
            onClick = {
                detail(it.id_mk.toString(), it.namaMatkul.toString())
            }
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ProfileFragment.adapter
        }

        getMatkul()
        userProfile()

        binding.btnUpdate.setOnClickListener {
            update()
        }

        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun userProfile() {
        UserProfilePreferences.init(requireContext())
        val data = UserProfilePreferences.getUserProfile()

        binding.apply {
            name.text = data.name
            email.text = data.email
            isiNim.text = data.nim
            isiSms.text = data.semester
            isiProdi.text = data.prodi
            isiFkt.text = data.fakultas
        }
    }

    private fun getMatkul() {
        val preference = EncryptPreferences(requireContext())
        val token = preference.getPreferences().getString("token", null)

        viewModel.getMatkulProfile(token.toString(),
            onSuccess = {
                val pagingData: PagingData<Matkul> = PagingData.from(it)
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

    private fun update() {
        val fragment = UpdateFragment.newInstance()
        (requireActivity() as AppCompatActivity).supportFragmentManager
            .beginTransaction().apply {
                replace(R.id.fragment_container, fragment)
                addToBackStack(null)
                commit()
            }
    }

    private fun logout() {
        val token = preference.getPreferences().getString("token", null)

        viewModel.logout(token.toString(),
            onSuccess = {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                logoutProses()
            },
            onFailure = {
                logoutProses()
                Toast.makeText(
                    requireContext(),
                    getString(R.string.messageLogout),
                    Toast.LENGTH_SHORT
                ).show()
            },
            loading = {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            })
    }

    private fun logoutProses() {
        UserProfilePreferences.init(requireContext())
        UserProfilePreferences.removeUserProfile()
        preference.removePreferences()

        val repository: JadwalRepository = JadwalRepository.getInstance(requireContext())
        lifecycleScope.launch {
            repository.deleteAll()
        }

        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun detail(id: String, name: String) {
        val fragment = MeetFragment.newInstance(id, name)
        (requireActivity() as AppCompatActivity).supportFragmentManager
            .beginTransaction().apply {
                replace(R.id.fragment_container, fragment)
                addToBackStack(null)
                commit()
            }
    }
}