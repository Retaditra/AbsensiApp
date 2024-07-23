package com.absensi.app.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.absensi.app.data.Pertemuan
import com.absensi.app.databinding.HistoryBinding

class HomeAdapter(private val absent: (Pertemuan) -> Unit) :
    PagingDataAdapter<Pertemuan, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            HistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val pertemuan = getItem(position)
            pertemuan?.let { holder.bind(it) }
        }
    }

    inner class ItemViewHolder(private val binding: HistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pertemuan: Pertemuan) {
            val jamText = "${pertemuan.tanggal} WIB"
            with(binding) {
                nameMK.text = pertemuan.namaMatkul
                ptm.text = pertemuan.pertemuan_ke
                tanggal.text = pertemuan.tanggal
                jam.text = jamText
                nameDsn.text = pertemuan.namaDosen

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
