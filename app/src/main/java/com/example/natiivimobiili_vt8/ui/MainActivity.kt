package com.example.natiivimobiili_vt8.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.natiivimobiili_vt8.model.Todo
import com.example.natiivimobiili_vt8.ui.theme.Natiivimobiilivt8Theme
import com.example.natiivimobiili_vt8.viewmodel.TodoUIState
import com.example.natiivimobiili_vt8.viewmodel.TodoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Natiivimobiilivt8Theme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                ) {
                    TodoApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoApp(todoViewModel: TodoViewModel = viewModel()) {
//    TodoScreen(uiState = todoViewModel.todoUIState)
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Todos") }
        )
    },
        // Tämä on se Material3 TopAppBar ominaisuus, eli on PAKKO antaa content parametri
        // ja se on PAKKO olla tuo innerPadding mukana. TodoScreenillä ei semmoista ole, niin
        // huijataan laittamalla se Column komponentin sisään, koska Columnille voi antaa padding parametrin.
        content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                TodoScreen(
                    uiState = todoViewModel.todoUIState
                )
            }
        }
    )
}

@Composable
fun TodoScreen(uiState: TodoUIState) {
    when (uiState) {
        is TodoUIState.Loading -> LoadingScreen()
        is TodoUIState.Success -> TodoList(uiState.todos)
        is TodoUIState.Error -> ErrorScreen()
    }
}

@Composable
fun LoadingScreen() {
    Text("Loading")
}

@Composable
fun ErrorScreen() {
    Text("Error retrieving data from API!")
}


@Composable
fun TodoList(todos: List<Todo>) {
    LazyColumn(modifier = Modifier.padding(8.dp)) {
        items(todos.size) { index ->
            Text(
                text = todos[index].title,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            )
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        }
    }
}

