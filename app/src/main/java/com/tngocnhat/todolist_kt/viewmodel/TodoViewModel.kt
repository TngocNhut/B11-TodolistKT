package com.tngocnhat.todolist_kt.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tngocnhat.todolist_kt.data.db.AppDatabase
import com.tngocnhat.todolist_kt.data.model.Todo
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val todoDao = AppDatabase.getDatabase(application).todoDao()

    private val _currentUserId = MutableLiveData<Int>()
    
    // Using switchMap if we needed to react to user changes, but for simplicity we'll just expose a function 
    // or assume the user ID is set once per session. 
    // Better: Expose a function to load todos for a user.
    
    fun getTodosForUser(userId: Int): LiveData<List<Todo>> {
        return todoDao.getTodosForUser(userId).asLiveData()
    }

    private val _currentTodo = MutableLiveData<Todo?>()
    val currentTodo: LiveData<Todo?> = _currentTodo

    fun selectTodo(todo: Todo) {
        _currentTodo.value = todo
    }

    fun clearSelectedTodo() {
        _currentTodo.value = null
    }

    fun addTodo(title: String, description: String?, userId: Int) {
        viewModelScope.launch {
            val todo = Todo(title = title, description = description, userId = userId)
            todoDao.insertTodo(todo)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            todoDao.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            todoDao.deleteTodo(todo)
        }
    }
}
