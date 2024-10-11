package com.example.natiivimobiili_vt8.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.natiivimobiili_vt8.model.TodosApi
import com.example.natiivimobiili_vt8.model.Todo
import kotlinx.coroutines.launch

sealed interface TodoUIState {
    data class Success(val todos: List<Todo>) : TodoUIState
    data object Error : TodoUIState
    data object Loading : TodoUIState
}

class TodoViewModel : ViewModel() {
    var todoUIState: TodoUIState by mutableStateOf(TodoUIState.Loading)

    init {
        getTodosList()
    }

    private fun getTodosList() {
        viewModelScope.launch {
            val todosApi: TodosApi?
            try {
                todosApi = TodosApi.getInstance()
                todoUIState = TodoUIState.Success(todosApi.getTodos())
            } catch (e: Exception) {
                Log.d("TodoApp", "Error: ${e.message}")
                todoUIState = TodoUIState.Error
            }
        }
    }
}