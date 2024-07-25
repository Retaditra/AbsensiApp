package com.absensi.app.jadwal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.absensi.app.data.Matkul
import com.absensi.app.databinding.JadwalBinding
import com.absensi.app.utils.formatDate

class JadwalAdapter(
    private val onClick: (Matkul) -> Unit
) :
    PagingDataAdapter<Matkul, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            JadwalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val schedule = getItem(position)
            schedule?.let { holder.bind(it) }
        }
    }

    inner class ItemViewHolder(private val binding: JadwalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(schedule: Matkul) {
            val dateFormat = schedule.tanggal?.let { formatDate(it) }

            with(binding) {
                nameMk.text = schedule.namaMatkul
                date.text = dateFormat
            }

            itemView.setOnClickListener {
                onClick(schedule)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Matkul>() {
            override fun areItemsTheSame(oldItem: Matkul, newItem: Matkul): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Matkul, newItem: Matkul): Boolean {
                return oldItem == newItem
            }
        }
    }
}
