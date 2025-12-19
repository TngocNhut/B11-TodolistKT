package com.tngocnhat.todolist_kt.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tngocnhat.todolist_kt.data.db.AppDatabase
import com.tngocnhat.todolist_kt.data.model.User
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getDatabase(application).userDao()

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean> = _navigateToHome

    private val _loginError = MutableLiveData<String?>()
    val loginError: LiveData<String?> = _loginError

    fun register(username: String, pass: String) {
        viewModelScope.launch {
             val existing = userDao.getUserByUsername(username)
             if (existing != null) {
                 _loginError.value = "User already exists"
                 return@launch
             }
             val newUser = User(username = username, password = pass)
             userDao.registerUser(newUser)
             login(username, pass)
        }
    }

    fun login(username: String, pass: String) {
        viewModelScope.launch {
            val user = userDao.login(username, pass)
            if (user != null) {
                _currentUser.value = user
                _navigateToHome.value = true
                _loginError.value = null
            } else {
                _loginError.value = "Invalid credentials"
            }
        }
    }
    
    fun logout() {
        _currentUser.value = null
        _navigateToHome.value = false
    }

    fun onNavigationDone() {
        _navigateToHome.value = false
    }
}
