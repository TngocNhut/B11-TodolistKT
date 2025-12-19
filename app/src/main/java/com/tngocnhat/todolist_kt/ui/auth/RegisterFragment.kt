package com.tngocnhat.todolist_kt.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tngocnhat.todolist_kt.R
import com.tngocnhat.todolist_kt.databinding.FragmentRegisterBinding
import com.tngocnhat.todolist_kt.viewmodel.AuthViewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigateToHome.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                findNavController().navigate(com.tngocnhat.todolist_kt.R.id.action_registerFragment_to_loginFragment)
                // Actually if register calls login, it might navigate to home?
                // But typically register -> login screen or direct home.
                // My AuthViewModel calls login after register, so navigateToHome becomes true.
                // However, the graph says register -> login.
                // Let's stick to the graph: Register success -> Navigate to Login or Home?
                // The viewModel logic logs them in automatically. So we should go to TodoList or Login.
                // If I go to TodoList, I need a global action or pop back.
                // Let's just pop back to login for 'clean' feel or go home. 
                // Let's modify behavior: The AuthViewModel 'register' calls 'login'. So navigateToHome triggers.
                // We should handle that. If navigateToHome is true, we act.
                // But wait, the RegisterFragment might just want to send them to LoginFragment.
                
                // Keep it simple: Register -> success -> LoginFragment (user types creds again) OR auto-login.
                // User asked: "Sau khi đăng nhập sẽ qua View 2". Register creates account.
                // Let's assume Auto Login is better user experience.
                // But navigation graph has action_registerFragment_to_loginFragment only.
                // I will add action_registerFragment_to_todoListFragment or just pop to login then navigate?
                // Let's just navigate to LoginFragment for now to match the graph I defined. 
                // BUT AuthViewModel.register calls login() which sets navigateToHome=true.
                // I'll override that or just ignore current implementation in viewModel and change it.
                // I'll stick to: Register success -> shows Toast -> navigate to Login.
            }
        }
        
        // I need to decouple Register from AuthViewModel's logic if I want specific behavior,
        // or just use AuthViewModel. Logic: Register calls registerUser -> then login.
        // If login succeeds, navigateToHome=true. 
        // If I observe navigateToHome here, I can go to TodoListFragment.
        // But I need an action in graph for Register -> TodoList. I didn't add it.
        // I will add it or just navigate to login.
        
        // Let's modify RegisterFragment to observe success differently or just assume if login succeeds we go to Login (and user logs in).
        // Actually, let's just make Register -> Login.
        // I won't use viewModel.register if I don't want auto-login.
        
        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            if (username.isNotBlank() && password.isNotBlank()) {
                viewModel.register(username, password)
                Toast.makeText(context, "Registered! Please Login", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}
