package com.example.myapplication.view.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.WorkoutModel


class WorkoutListAdapter(val onItemPressed:(WorkoutModel)->Unit
) : ListAdapter<WorkoutModel, WorkoutListAdapter.WorkoutListViewHolder>(Companion) {


    class WorkoutListViewHolder(val view: View) :
        RecyclerView.ViewHolder(view)

    companion object : DiffUtil.ItemCallback<WorkoutModel>() {
        override fun areItemsTheSame(oldItem: WorkoutModel, newItem: WorkoutModel) =
            oldItem == newItem


        override fun areContentsTheSame(oldItem: WorkoutModel, newItem: WorkoutModel) =
            newItem.desc == oldItem.desc && newItem.time == oldItem.time

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutListViewHolder =
        WorkoutListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.workout_item, parent, false)
        )


    override fun onBindViewHolder(holder: WorkoutListViewHolder, position: Int) {
        holder.view.rootView.setOnClickListener {
            onItemPressed(getItem(position))
        }
        holder.view.findViewById<TextView>(R.id.workout_item_desc).text = getItem(position).desc
        holder.view.findViewById<TextView>(R.id.workout_Item_time).text = getItem(position).time
    }
}