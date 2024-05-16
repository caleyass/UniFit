package com.unifit.unifit.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.unifit.unifit.data.local.entity.AnalysisEntity
import com.unifit.unifit.databinding.ItemRvTextDescriptionIvBinding

class AnalysisAdapter() : ListAdapter<AnalysisEntity, AnalysisAdapter.AnalysisViewHolder>(DiffCallback) {
    class AnalysisViewHolder(private val binding: ItemRvTextDescriptionIvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind (analysis: AnalysisEntity){
            binding.apply {
                tvItemName.text = analysis.name
                tvItemDescription.text = "Test date:\n25.05"
                /*val dateDiff = ChronoUnit.DAYS.between(LocalDate.now(), pill.endDate)
                if(dateDiff <= 0L)
                    tvItemDescription.text = "Completed"
                else
                    tvItemDescription.text = binding.root.context.getString(R.string.day_left, dateDiff)*/

            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<AnalysisEntity>() {
            override fun areItemsTheSame(oldItem: AnalysisEntity, newItem: AnalysisEntity): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: AnalysisEntity, newItem: AnalysisEntity): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalysisViewHolder {
        return AnalysisViewHolder(
            ItemRvTextDescriptionIvBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: AnalysisViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}