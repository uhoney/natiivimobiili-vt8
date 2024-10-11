package com.example.natiivimobiili_vt8.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.natiivimobiili_vt8.model.TodosApi
import com.example.natiivimobiili_vt8.model.Todo
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {
    val todos = mutableStateListOf<Todo>()

    init {
        getTodosList()
    }

    private fun getTodosList() {
        viewModelScope.launch {
            var todosApi: TodosApi? = null
            try {
                todosApi = TodosApi.getInstance()
                todos.clear()
                val apiTodos = todosApi.getTodos()
                if (apiTodos.isNotEmpty()) {
                    todos.addAll(apiTodos)
                }
            } catch (e: Exception) {
                Log.d("TodoApp", "Error: ${e.message}")
            }
        }
    }
}