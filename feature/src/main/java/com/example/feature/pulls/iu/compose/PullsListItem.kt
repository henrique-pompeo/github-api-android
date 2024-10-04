package com.example.feature.pulls.iu.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.feature.pulls.domain.model.PullModel
import com.example.feature.pulls.domain.model.PullUserModel

@Composable
fun PullsListItem(pullModel: PullModel) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .border(width = 0.5.dp, color = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        ListItem(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(150.dp),
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
            overlineContent = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = pullModel.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            headlineContent = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = pullModel.createdAt.format(Locale.current, "dd/MM/yyyy"),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            supportingContent = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = pullModel.body?: "Sem descrição",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2,
                        minLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    UserSection(pullModel.user)
                }
            }
        )
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
            ) {
                it.diskCacheStrategy(DiskCacheStrategy.DATA)
            }
        }
        Text(
            text = user.login,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = false)
@Composable
fun PullsListItemPreview() {
    PullsListItem(
        PullModel(
            title = "Pull request 1",
            body = "Descrição da pull request aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
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
