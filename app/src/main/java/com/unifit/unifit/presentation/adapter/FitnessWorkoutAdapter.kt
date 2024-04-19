package com.unifit.unifit.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.unifit.unifit.R
import com.unifit.unifit.domain.data.FitnessWorkout

class FitnessWorkoutAdapter(
    private val onFitnessWorkoutClicked: (String) -> Unit
) : PagingDataAdapter<FitnessWorkout, FitnessWorkoutAdapter.FitnessWorkoutViewHolder>(Companion) {
    companion object : DiffUtil.ItemCallback<FitnessWorkout>() {
        override fun areItemsTheSame(oldItem: FitnessWorkout, newItem: FitnessWorkout): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: FitnessWorkout, newItem: FitnessWorkout): Boolean {
            return oldItem == newItem
        }
    }

    // ViewHolder class
    inner class FitnessWorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tv_fitness_program)
        //val imageView: ImageView = itemView.findViewById(R.id.iv_fitness_program)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val fitnessWorkout = textView.text.toString()
                    onFitnessWorkoutClicked(fitnessWorkout)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: FitnessWorkoutViewHolder, position: Int) {
        val fitnessProgram = getItem(position) ?: return
        holder.textView.text = fitnessProgram.name
        /*Glide
            .with(holder.imageView.context)
            .load(fitnessProgram.imageUri)
            .into(holder.imageView)*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FitnessWorkoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_layout_fitness_program, parent, false)
        return FitnessWorkoutViewHolder(view)
    }

}