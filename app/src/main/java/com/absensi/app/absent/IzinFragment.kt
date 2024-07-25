package com.absensi.app.absent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.viewModels
import com.absensi.app.R
import com.absensi.app.data.respone.IzinRequest
import com.absensi.app.databinding.FragmentIzinBinding
import com.absensi.app.utils.EncryptPreferences
import com.absensi.app.utils.expired
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class IzinFragment : BottomSheetDialogFragment() {

    private lateinit var preference: EncryptPreferences
    private lateinit var binding: FragmentIzinBinding
    private val viewModel: AbsentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIzinBinding.inflate(inflater, container, false)

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
            val ket = binding.ketAbsent.text.toString()
            if (isInputValid(ket)) {
                izin(id, ket)
            }
        }
    }

    private fun izin(id: Int, ket: String) {
        val token = preference.getPreferences().getString("token", null)
        viewModel.izin(token.toString(),
            request = IzinRequest(id.toString(), ket),
            onSuccess = {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
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

    companion object {
        fun newInstance(id: String, name: String, ptm: String): IzinFragment {
            val fragment = IzinFragment()
            val args = Bundle()
            args.putString("id", id)
            args.putString("name", name)
            args.putString("ptm", ptm)
            fragment.arguments = args
            return fragment
        }
    }
}