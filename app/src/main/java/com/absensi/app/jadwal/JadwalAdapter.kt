package com.absensi.app.jadwal

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.absensi.app.data.Jadwal
import com.absensi.app.databinding.JadwalBinding

class JadwalAdapter(
    private val onClick: (Jadwal) -> Unit
) :
    PagingDataAdapter<Jadwal, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

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

        fun bind(schedule: Jadwal) {
            with(binding) {
                nameMk.text = schedule.namaMatkul
                date.text = schedule.tanggal
            }
            itemView.setOnClickListener {
                onClick(schedule)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Jadwal>() {
            override fun areItemsTheSame(oldItem: Jadwal, newItem: Jadwal): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Jadwal, newItem: Jadwal): Boolean {
                return oldItem == newItem
            }
        }
    }
}
