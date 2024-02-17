package com.example.todo.ui.home.taskList

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.database.model.Task
import com.example.todo.databinding.ItemTaskBinding
import com.example.todo.ui.formatTime
import java.util.Calendar

class TaskAdapter(var tasks:MutableList<Task>?=null):
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

   class TaskViewHolder(val binding: ItemTaskBinding):RecyclerView.ViewHolder(binding.root){
       fun bind(task: Task, calendar: Calendar) {
           binding.title.text = task.title.toString()
           binding.description.text = calendar.formatTime()
           if (task.isDone == true) {
               binding.btnTaskIsDone.setImageResource(R.drawable.check_mark)
               binding.title.setTextColor(Color.GREEN)
               binding.draggingBar.setImageResource(R.drawable.dragging_bar_done)
           } else {
               binding.btnTaskIsDone.setImageResource(R.drawable.check_mark)
               binding.title.setTextColor(Color.parseColor("#5D9CEC"))
               binding.draggingBar.setImageResource(R.drawable.dragging_bar)
           }
       }

   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemBinding:ItemTaskBinding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context)
        ,parent,false)
        return TaskViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks!![position]
        val calendar = Calendar.Builder()
            .setInstant(task.time!!)
            .build()
        holder.bind(task, calendar)
        if (onTaskClickListener != null) {
            holder.binding.dragItem.setOnClickListener {
                onTaskClickListener?.onItemClick(task, position)
            }
        }
        if (onIsDoneClickListener != null) {
            holder.binding.btnTaskIsDone.setOnClickListener {
                onIsDoneClickListener?.onItemClick(task, position)
            }
        }
        if (onDeleteClickListener != null) {
            holder.binding.btnDeleteTask.setOnClickListener {
                onDeleteClickListener?.onItemClick(task, position)
            }
        }
    }

    override fun getItemCount(): Int = tasks?.size?:0

    var onTaskClickListener: OnItemClickListener? = null
    var onIsDoneClickListener: OnItemClickListener? = null
    var onDeleteClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(item: Task, id: Int)
    }
    fun changeData(allTasks: List<Task>) {
        if (tasks == null) {
            tasks = mutableListOf()
        }
        tasks?.clear()
        tasks?.addAll(allTasks)
        notifyDataSetChanged()

    }
}