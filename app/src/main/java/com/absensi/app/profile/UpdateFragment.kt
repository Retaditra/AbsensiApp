package com.absensi.app.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.absensi.app.R
import com.absensi.app.data.respone.UpdateRequest
import com.absensi.app.databinding.FragmentUpdateBinding
import com.absensi.app.utils.EncryptPreferences
import com.absensi.app.utils.expired
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UpdateFragment : BottomSheetDialogFragment() {

    private lateinit var preference: EncryptPreferences
    private lateinit var binding: FragmentUpdateBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)

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
        binding.btnUpdate.setOnClickListener {
            val current = binding.currentPassword.text.toString()
            val new = binding.newPassword.text.toString()
            val confirm = binding.confirmPassword.text.toString()

            if (isInputValid(current, new, confirm)) {
                getUpdate(current, new, confirm)
            }
        }
    }

    private fun getUpdate(current: String, new: String, confirm: String) {
        val preference = EncryptPreferences(requireContext())
        val token = preference.getPreferences().getString("token", null)

        viewModel.updateProfile(token.toString(),
            request = UpdateRequest(current, new, confirm),
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
            })
    }

    private fun isInputValid(current: String, new: String, confirm: String): Boolean {
        var isValid = true

        if (current.isEmpty()) {
            binding.currentLayout.error = getString(R.string.noEmpty)
            isValid = false
        }
        if (new.isEmpty() || new.length < 8) {
            binding.newLayout.error = getString(R.string.invalid_password)
            isValid = false
        }
        if (confirm.isEmpty() || confirm != new) {
            binding.confirmLayout.error = getString(R.string.pass_not_march)
            isValid = false
        }
        return isValid
    }

    companion object {
        fun newInstance(): UpdateFragment {
            return UpdateFragment()
        }
    }
}
