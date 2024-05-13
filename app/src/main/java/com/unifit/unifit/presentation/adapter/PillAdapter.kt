package com.unifit.unifit.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.unifit.unifit.R
import com.unifit.unifit.data.local.entity.PillEntity
import com.unifit.unifit.databinding.ItemRvTextDescriptionBinding
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class PillAdapter(
    private val onItemClicked: (PillEntity) -> Unit
) : ListAdapter<PillEntity, PillAdapter.PillViewHolder>(DiffCallback) {

    class PillViewHolder(private val binding: ItemRvTextDescriptionBinding, private val onItemClicked: (PillEntity) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind (pill: PillEntity){
            binding.apply {
                tvItemName.text = pill.name
                //todo move it to domain layer
                val dateDiff = ChronoUnit.DAYS.between(LocalDate.now(), pill.endDate)
                if(dateDiff <= 0L)
                    tvItemDescription.text = "Completed"
                else
                    tvItemDescription.text = binding.root.context.getString(R.string.day_left, dateDiff)

                root.setOnClickListener {
                    onItemClicked(pill)
                }
            }
        }
    }



    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<PillEntity>() {
            override fun areItemsTheSame(oldItem: PillEntity, newItem: PillEntity): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: PillEntity, newItem: PillEntity): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PillViewHolder {
        return PillViewHolder(
            ItemRvTextDescriptionBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            ),
            onItemClicked
        )
    }

    override fun onBindViewHolder(holder: PillViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}