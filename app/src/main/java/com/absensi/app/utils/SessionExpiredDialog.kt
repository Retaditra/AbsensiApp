package com.kta.app.utils

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import com.kta.app.R
import com.kta.app.databinding.DialogSessionExpiredBinding
import com.kta.app.login.LoginActivity

class SessionExpiredDialog(private val context: Context) {

    private fun showDialog() {
        val binding = DialogSessionExpiredBinding.inflate(LayoutInflater.from(context))
        val dialog = AlertDialog.Builder(context)
            .setView(binding.root)
            .setCancelable(false)
            .create()
        binding.textMessage.text = context.getString(R.string.dialogRelog)

        binding.btnLogin.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
            dialog.dismiss()

            EncryptPreferences(context).removePreferences()
        }

        dialog.show()
    }

    companion object {
        fun show(context: Context) {
            SessionExpiredDialog(context).showDialog()
        }
    }
}

