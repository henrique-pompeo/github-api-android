package com.example.feature.pulls.iu.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.feature.pulls.domain.model.PullModel
import com.example.feature.pulls.domain.model.PullUserModel

@Composable
fun PullsListItem(pullModel: PullModel) {
    Box {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(150.dp)
        ) {
            ListItem(
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                overlineContent = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = pullModel.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                headlineContent = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = pullModel.createdAt.format(Locale.current, "dd/MM/yyyy"),
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                supportingContent = {
                    Column {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = pullModel.body?: "Sem descrição",
                            style = MaterialTheme.typography.bodySmall
                        )
                        UserSection(pullModel.user)
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserSection(user: PullUserModel) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .padding(end = 10.dp)
                .size(30.dp),
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(1.dp)
        ) {
            GlideImage(
                model = user.avatarUrl,
                contentDescription = "Avatar de ${user.login}"
            )
        }
        Text(
            text = user.login
        )
    }
}

@Preview(showBackground = false)
@Composable
fun PullsListItemPreview() {
    PullsListItem(
        PullModel(
            title = "Pull request 1",
            body = "Descrição da pull request",
            createdAt = "2023-06-01T12:00:00Z",
            user = PullUserModel(
                login = "henrique-pompeo-modesto",
                avatarUrl = "https://avatars.githubusercontent.com/u/26586900?v=4"
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
fun UserSectionPreview() {
    UserSection(
        PullUserModel(
            login = "henrique-pompeo-modesto",
            avatarUrl = "https://avatars.githubusercontent.com/u/26586900?v=4"
        )
    )
}
