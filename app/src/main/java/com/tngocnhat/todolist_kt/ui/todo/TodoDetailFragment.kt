package com.tngocnhat.todolist_kt.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tngocnhat.todolist_kt.R
import com.tngocnhat.todolist_kt.databinding.FragmentTodoDetailBinding
import com.tngocnhat.todolist_kt.viewmodel.AuthViewModel
import com.tngocnhat.todolist_kt.viewmodel.TodoViewModel
import com.tngocnhat.todolist_kt.data.model.Todo

class TodoDetailFragment : Fragment() {

    private lateinit var binding: FragmentTodoDetailBinding
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoDetailBinding.inflate(inflater, container, false)
        todoViewModel = ViewModelProvider(requireActivity()).get(TodoViewModel::class.java)
        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        
        binding.viewModel = todoViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = authViewModel.currentUser.value

        todoViewModel.currentTodo.observe(viewLifecycleOwner) { todo ->
             if (todo != null) {
                 binding.btnDelete.visibility = View.VISIBLE
                 binding.tvHeaderTitle.text = getString(R.string.edit_todo)
                 binding.etTitle.setText(todo.title)
                 binding.etDescription.setText(todo.description)
             } else {
                 binding.btnDelete.visibility = View.GONE
                 binding.tvHeaderTitle.text = getString(R.string.new_todo)
                 binding.etTitle.setText("")
                 binding.etDescription.setText("")
             }
        }

        // Back button
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // Save button
        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val description = binding.etDescription.text.toString()

            if (title.isBlank()) {
                Toast.makeText(context, "Title required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (currentUser != null) {
                val currentTodo = todoViewModel.currentTodo.value
                if (currentTodo != null) {
                    val updated = currentTodo.copy(title = title, description = description)
                    todoViewModel.updateTodo(updated)
                } else {
                    todoViewModel.addTodo(title, description, currentUser.id)
                }
                findNavController().popBackStack()
            } else {
                Toast.makeText(context, "User not logged in!", Toast.LENGTH_SHORT).show()
            }
        }

        // Delete button
        binding.btnDelete.setOnClickListener {
            val currentTodo = todoViewModel.currentTodo.value
            if (currentTodo != null) {
                todoViewModel.deleteTodo(currentTodo)
                findNavController().popBackStack()
            }
        }
    }
}

