package com.example.feature.pulls.iu.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feature.pulls.domain.model.PullModel
import com.example.feature.pulls.iu.PullsState
import com.example.feature.pulls.iu.viewmodel.PullsViewModel

@Composable
fun PullsScreen(
    viewModel: PullsViewModel,
    owner: String,
    repo: String
) {

    val state by viewModel.pullsState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getPulls(owner, repo)
    }

    Scaffold(
        topBar = { Header(repo = repo) }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            when (state) {
                is PullsState.Success -> SetupSuccess(items = (state as PullsState.Success).pulls)
                is PullsState.Empty -> TODO("EMPTY STATE")
                is PullsState.Error -> SetupError(tryAgain = { viewModel.getPulls(owner, repo) } )
                is PullsState.Loading -> SetupLoading()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(repo: String) {
    TopAppBar(
        title = {
            Text(text = repo)
        },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar para a lista de repositórios"
                )
            }
        }
    )
}

@Composable
fun SetupSuccess(items: List<PullModel>) {
    LazyColumn {
        items(items) { item ->
            PullsListItem(pullModel = item)
        }
    }
}

@Composable
fun SetupError(tryAgain: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.TopCenter),
            onClick = { tryAgain.invoke() }
        ) {
            Icon(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.Filled.Refresh,
                contentDescription = "Menu"
            )
        }
        Text(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = "Falha ao carregar lista"
        )
    }
}

@Composable
fun SetupLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
        Text(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = "Carregando lista de pulls..."
        )
    }
}