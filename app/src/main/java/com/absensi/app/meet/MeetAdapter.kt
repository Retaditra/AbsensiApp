package com.absensi.app.meet

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.absensi.app.data.Pertemuan
import com.absensi.app.databinding.PertemuanBinding
import com.absensi.app.utils.ButtonUtils
import com.absensi.app.utils.formatDate

class MeetAdapter(
    private val context: Context,
    private val absent: (Pertemuan) -> Unit
) :
    PagingDataAdapter<Pertemuan, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            PertemuanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val pertemuan = getItem(position)
            pertemuan?.let { holder.bind(it) }
        }
    }

    inner class ItemViewHolder(private val binding: PertemuanBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pertemuan: Pertemuan) {
            val jamText = "${pertemuan.waktu} WIB"
            val date = pertemuan.tanggal?.let { formatDate(it) }
            with(binding) {
                isiPtm.text = pertemuan.pertemuan_ke
                semester.text = pertemuan.semester
                tanggal.text = date
                jam.text = jamText
                nameDsn.text = pertemuan.namaDosen

                ButtonUtils.setButtonStatus(btnAbsent, pertemuan)
                ButtonUtils.setButtonTextAndStyle(btnAbsent, pertemuan)
                ButtonUtils.setButtonBackgroundColor(context, btnAbsent, pertemuan)

                btnAbsent.setOnClickListener {
                    absent(pertemuan)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pertemuan>() {
            override fun areItemsTheSame(oldItem: Pertemuan, newItem: Pertemuan): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Pertemuan, newItem: Pertemuan): Boolean {
                return oldItem == newItem
            }
        }
    }
}