package com.absensi.app.absent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.absensi.app.R
import com.absensi.app.data.respone.AbsentRequest
import com.absensi.app.databinding.FragmentAbsentBinding
import com.absensi.app.utils.EncryptPreferences
import com.absensi.app.utils.expired
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AbsentFragment : BottomSheetDialogFragment() {

    private lateinit var preference: EncryptPreferences
    private lateinit var binding: FragmentAbsentBinding
    private val viewModel: AbsentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAbsentBinding.inflate(inflater, container, false)

        preference = EncryptPreferences(requireContext())

        setupView()
        setupButton()

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
        val id = arguments?.getString("id")?.toIntOrNull() ?: 0
        val name = arguments?.getString("name").toString()
        val ptm = arguments?.getString("ptm").toString()

        binding.nameMk.text = name
        binding.isiPtm.text = ptm

        binding.btnAbsent.setOnClickListener {
            val code = binding.codeAbsent.text.toString()
            if (isInputValid(code)) {
                absent(id, code)
            }
        }

        binding.izin.setOnClickListener {
            izin(id.toString(), name, ptm)
        }
    }

    private fun absent(id: Int, code: String) {
        val token = preference.getPreferences().getString("token", null)
        viewModel.absent(token.toString(),
            request = AbsentRequest(id.toString(), code),
            onSuccess = {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            },
            onFailure = {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                expired(it, requireContext())
            },
            loading = {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }
        )
    }

    private fun isInputValid(code: String): Boolean {
        var isValid = true
        if (code.isEmpty()) {
            binding.codeLayout.error = getString(R.string.noEmpty)
            isValid = false
        }
        return isValid
    }

    private fun izin(id: String, name: String, ptm: String) {
        val fragment = IzinFragment.newInstance(id, name, ptm)
        (requireActivity() as AppCompatActivity).supportFragmentManager
            .beginTransaction().apply {
                replace(R.id.container_izin, fragment)
                addToBackStack(null)
                commit()
            }
    }

    companion object {
        fun newInstance(id: String, name: String, ptm: String): AbsentFragment {
            val fragment = AbsentFragment()
            val args = Bundle()
            args.putString("id", id)
            args.putString("name", name)
            args.putString("ptm", ptm)
            fragment.arguments = args
            return fragment
        }
    }
}