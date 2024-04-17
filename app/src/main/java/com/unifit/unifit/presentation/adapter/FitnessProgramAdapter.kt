package com.unifit.unifit.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.unifit.unifit.R
import com.unifit.unifit.data.remote.dto.FitnessCategory

class FitnessProgramAdapter(
    private val fitnessCategories: List<FitnessCategory>,
    private val onFitnessProgramClicked: (String) -> Unit
) : RecyclerView.Adapter<FitnessProgramAdapter.FitnessProgramViewHolder>() {

    // ViewHolder class
    inner class FitnessProgramViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tv_fitness_program)
        val imageView: ImageView = itemView.findViewById(R.id.iv_fitness_program)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val fitnessProgram = fitnessCategories[position]
                    onFitnessProgramClicked(fitnessProgram.name)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FitnessProgramViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_layout_fitness_program, parent, false)
        return FitnessProgramViewHolder(view)
    }

    override fun onBindViewHolder(holder: FitnessProgramViewHolder, position: Int) {
        val fitnessProgram = fitnessCategories[position]
        holder.textView.text = fitnessProgram.name
        Glide
            .with(holder.imageView.context)
            .load(fitnessProgram.imageResource)
            .into(holder.imageView)
        //holder.imageView.setImageResource(fitnessProgram.imageResource)
    }

    override fun getItemCount(): Int {
        return fitnessCategories.size
    }
}
