package com.lumins.sua.views.email_alerts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lumins.sua.android.utils.viewModelFactory
import com.lumins.sua.data.local.db.EmailAlert
import com.lumins.sua.repo.SuaRepository
import com.lumins.sua.ui.theme.SUATheme
import com.lumins.sua.ui.theme.SuaColors
import java.time.Instant

@Composable
fun EmailAlertsScreen() {
    val context = LocalContext.current
    val viewModel: EmailAlertViewModel =
        viewModel(factory = viewModelFactory { EmailAlertViewModel(context) })
    val alerts = viewModel.emailAlerts.collectAsStateWithLifecycle()

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(alerts.value) { alert ->
            EmailAlertItem(alert) { viewModel.deleteEmailAlert(it)  }
        }
    }

}


@Composable
fun EmailAlertItem(alert: EmailAlert, onDelete: (EmailAlert) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        Modifier
            .fillMaxWidth()
    ) {
        Column(
            Modifier
                .fillMaxWidth(),
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                IconButton( onClick = { onDelete(alert) }) {
                    Icon(
                        imageVector = Icons.Rounded.DeleteForever,
                        contentDescription = "delete",
                        tint = SuaColors.darkRedMisc
                    )
                }

                IconButton(onClick = { isExpanded = isExpanded.not() }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Rounded.KeyboardArrowDown else Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                        contentDescription = "expand",
                    )
                }
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 8.dp),
            ) {
                Text(text = alert.subject, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))


                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Person,
                        contentDescription = "email",
                        Modifier.size(24.dp)
                    )
                    Text(
                        text = alert.email,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                AnimatedVisibility(visible = isExpanded) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = alert.body)

                }
            }

        }

    }
}


@Preview
@Composable
fun EmailAlertsScreenPreview() {
    val alert = EmailAlert(
        1,
        "Email Name <email@email.com>",
        "Email Subject is this. Email Subject is this. Email Subject is this. Email Subject is this.",
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
        "www.google.com${SuaRepository.LINKS_DELIMITER}www.facebook.com",
        Instant.now().toEpochMilli().toString() // epoch millis
    )
    SUATheme {
        EmailAlertItem(alert) {}
    }
}