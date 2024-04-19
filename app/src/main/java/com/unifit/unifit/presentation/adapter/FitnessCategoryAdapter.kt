package com.unifit.unifit.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.unifit.unifit.R
import com.unifit.unifit.domain.data.FitnessCategory


class FitnessCategoryAdapter(
    private val onFitnessProgramClicked: (String) -> Unit
) : PagingDataAdapter<FitnessCategory, FitnessCategoryAdapter.FitnessCategoryViewHolder>(Companion) {
    companion object : DiffUtil.ItemCallback<FitnessCategory>() {
        override fun areItemsTheSame(oldItem: FitnessCategory, newItem: FitnessCategory): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: FitnessCategory, newItem: FitnessCategory): Boolean {
            return oldItem == newItem
        }
    }
    // ViewHolder class
    inner class FitnessCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tv_fitness_program)
        val imageView: ImageView = itemView.findViewById(R.id.iv_fitness_program)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val fitnessProgram = textView.text.toString()
                    onFitnessProgramClicked(fitnessProgram)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FitnessCategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_layout_fitness_program, parent, false)
        return FitnessCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: FitnessCategoryViewHolder, position: Int) {
        val fitnessProgram = getItem(position) ?: return
        holder.textView.text = fitnessProgram.name
        Glide
            .with(holder.imageView.context)
            .load(fitnessProgram.imageUri)
            .into(holder.imageView)
    }

}