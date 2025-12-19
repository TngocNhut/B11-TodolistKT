package com.tngocnhat.todolist_kt.ui.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tngocnhat.todolist_kt.data.model.Todo
import com.tngocnhat.todolist_kt.databinding.TodoItemBinding

class TodoAdapter(private val onEditClick: (Todo) -> Unit) :
    ListAdapter<Todo, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TodoViewHolder(private val binding: TodoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(todo: Todo) {
            binding.todo = todo
            binding.tvIndex.text = "${adapterPosition + 1}" // 1-based index
            
            // Format date
            val sdf = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
            binding.tvDate.text = sdf.format(java.util.Date(todo.createdDate))
            
            binding.btnEdit.setOnClickListener { onEditClick(todo) }
            binding.root.setOnClickListener { onEditClick(todo) } // Also click entry to edit as per user request
            binding.executePendingBindings()
        }
    }

    class TodoDiffCallback : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }
}
