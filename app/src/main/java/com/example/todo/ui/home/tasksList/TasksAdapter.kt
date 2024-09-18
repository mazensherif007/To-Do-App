package com.example.todo.ui.home.tasksList

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.todo.R
import com.example.todo.app.model.Task
import com.example.todo.databinding.ItemTaskBinding
import com.example.todo.formatTimeOnly

class TasksAdapter(private var task: MutableList<Task>? = null): Adapter<TasksAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemTaskBinding): RecyclerView.ViewHolder(binding.root){

        fun changeTaskStatus(isDone: Boolean){
            if (isDone){
                binding.draggingBar.setImageResource(R.drawable.dragging_bar_done)
                binding.title.setTextColor(Color.GREEN)
                binding.btnTaskIsDone.setBackgroundResource(R.drawable.done)
            } else {
                val blue = ContextCompat.getColor(itemView.context, R.color.blue)
                binding.title.setTextColor(blue)
                binding.draggingBar.setImageResource(R.drawable.dragging_bar)
                binding.btnTaskIsDone.setBackgroundResource(R.drawable.check_mark)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = task?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = task?.get(position)
        holder.binding.title.text = task?.title
        holder.binding.time.text = task?.time?.formatTimeOnly()
        holder.changeTaskStatus(task?.status!!)

        onTaskClickListener?.let {
            holder.binding.dragItem.setOnClickListener{
                onTaskClickListener?.onItemClick(position, task)
            }
        }

        onDoneBtnClickListener?.let {
            holder.binding.btnTaskIsDone.setOnClickListener {
                onDoneBtnClickListener?.onItemClick(position, task)
            }
        }

        onClick.let {
            holder.binding.dragItem.setOnClickListener{
                onClick?.invoke(position,task)
            }
        }

        onDeleteClick.let {
            holder.binding.leftView.setOnClickListener{
                onDeleteClick?.invoke(position,task)
            }
        }
    }

    fun submitNewData(newTasks: MutableList<Task>) {
        task = newTasks
        notifyDataSetChanged()
    }

    var onTaskClickListener: OnItemClickListener? = null
    var onDoneBtnClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(position: Int,task: Task)
    }

    fun deleteTask(position: Int, task: Task) {
        this.task?.remove(task)
        notifyItemRemoved(position)
    }

    var onClick: ((Int, Task) -> Unit) ? = null
    var onDeleteClick: ((Int, Task) -> Unit) ? = null

}