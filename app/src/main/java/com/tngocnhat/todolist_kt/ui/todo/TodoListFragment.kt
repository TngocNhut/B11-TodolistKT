package com.tngocnhat.todolist_kt.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tngocnhat.todolist_kt.R
import com.tngocnhat.todolist_kt.databinding.FragmentTodoListBinding
import com.tngocnhat.todolist_kt.viewmodel.AuthViewModel
import com.tngocnhat.todolist_kt.viewmodel.TodoViewModel

class TodoListBinding
class TodoListFragment : Fragment() {

    private lateinit var binding: FragmentTodoListBinding
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoListBinding.inflate(inflater, container, false)
        // TodoViewModel can be scoped to Fragment, but better Activity if we want to share with detail 
        // OR share via Activity. Let's share via Activity for simplicity as detail needs to know 'currentTodo'.
        todoViewModel = ViewModelProvider(requireActivity()).get(TodoViewModel::class.java)
        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        
        binding.viewModel = todoViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TodoAdapter { todo ->
            todoViewModel.selectTodo(todo)
            findNavController().navigate(R.id.action_todoListFragment_to_todoDetailFragment)
        }
        binding.rvTodos.layoutManager = LinearLayoutManager(context)
        binding.rvTodos.adapter = adapter

        authViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                todoViewModel.getTodosForUser(user.id).observe(viewLifecycleOwner) { todos ->
                    adapter.submitList(todos)
                    
                    // Show/hide empty state
                    if (todos.isEmpty()) {
                        binding.emptyStateLayout.visibility = View.VISIBLE
                        binding.rvTodos.visibility = View.GONE
                    } else {
                        binding.emptyStateLayout.visibility = View.GONE
                        binding.rvTodos.visibility = View.VISIBLE
                    }
                }
            } else {
                 // Not logged in? Navigate back to login?
                 findNavController().navigate(R.id.action_todoListFragment_to_loginFragment)
            }
        }

        binding.fabAdd.setOnClickListener {
            todoViewModel.clearSelectedTodo()
            findNavController().navigate(R.id.action_todoListFragment_to_todoDetailFragment)
        }

        // Logout button
        binding.btnLogout.setOnClickListener {
            authViewModel.logout()
            findNavController().navigate(R.id.action_todoListFragment_to_loginFragment)
        }
    }
}
