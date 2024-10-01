package com.example.feature.repos.iu.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.feature.repos.domain.model.ReposModel
import com.example.feature.repos.domain.model.ReposOwnerModel
import com.example.feature.repos.iu.ReposState
import com.example.feature.repos.iu.viewmodel.ReposViewModel

@Composable
fun ReposScreen(viewModel: ReposViewModel) {

    val state by viewModel.reposState.collectAsState()
    val currentItems = remember { mutableListOf<ReposModel>() }

    LaunchedEffect(key1 = Unit) {
        viewModel.getRepos()
    }

    Scaffold(
        topBar = { Header() }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            when (state) {
                is ReposState.Success -> {
                    currentItems.addAll((state as ReposState.Success).repos.items)
                    SetupSuccess(
                        items = currentItems,
                        loadMore = viewModel::loadMore
                    )
                }
                is ReposState.LoadMore -> {
                    currentItems.addAll((state as ReposState.LoadMore).repos.items)
                    SetupSuccess(
                        items = currentItems,
                        loadMore = viewModel::loadMore
                    )
                }
                is ReposState.Error -> SetupError(viewModel::getRepos)
                is ReposState.Loading -> SetupLoading()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Header() {
    TopAppBar(
        title = {
            Text(text = "Lista de repositórios")
        },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu"
                )
            }
        }
    )
}

@Composable
fun SetupSuccess(
    loadMore: () -> Unit,
    items: List<ReposModel>
) {
    val listState = rememberLazyListState()

    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 &&
                    lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom) loadMore()
    }

    LazyColumn(
        state = listState
    ) {
        items(items) { item ->
            ReposListItem(reposModel = item)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SetupSuccessPreview() {
    val items = listOf(
        ReposModel(
            name = "GitHub API Android",
            description = "App Android para demonstrar o conteúdo da API do GitHub",
            stargazersCount = 100,
            forksCount = 50,
            owner = ReposOwnerModel(
                login = "henrique-pompeo",
                avatarUrl = "https://avatars.githubusercontent.com/u/26586900?v=4"
            )
        ),
        ReposModel(
            name = "GitHub API Android",
            description = "App Android para demonstrar o conteúdo da API do GitHub",
            stargazersCount = 100,
            forksCount = 50,
            owner = ReposOwnerModel(
                login = "henrique-pompeo",
                avatarUrl = "https://avatars.githubusercontent.com/u/26586900?v=4"
            )
        ),
        ReposModel(
            name = "GitHub API Android",
            description = "App Android para demonstrar o conteúdo da API do GitHub",
            stargazersCount = 100,
            forksCount = 50,
            owner = ReposOwnerModel(
                login = "henrique-pompeo",
                avatarUrl = "https://avatars.githubusercontent.com/u/26586900?v=4"
            )
        )
    )

    SetupSuccess(
        loadMore = { },
        items = items
    )
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

@Preview(showBackground = true)
@Composable
fun SetupErrorPreview() {
    SetupError(tryAgain = {})
}

@Preview(showBackground = true)
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
            text = "Carregando lista de repositórios..."
        )
    }
}