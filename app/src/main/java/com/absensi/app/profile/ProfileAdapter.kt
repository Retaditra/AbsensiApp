package com.absensi.app.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.absensi.app.data.Jadwal
import com.absensi.app.databinding.MatkulBinding

class ProfileAdapter(private val onClick: (Jadwal) -> Unit) :
    PagingDataAdapter<Jadwal, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            MatkulBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val matkul = getItem(position)
            matkul?.let { holder.bind(it) }
        }
    }

    inner class ItemViewHolder(private val binding: MatkulBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pertemuan: Jadwal) {
            with(binding) {
                nameMK.text = pertemuan.namaMatkul
            }
            itemView.setOnClickListener {
                onClick(pertemuan)
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
