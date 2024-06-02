package com.lumins.sua.views.timetable

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lumins.sua.data.local.db.Timetable
import com.lumins.sua.ui.theme.generateRandomBrightColor

@Composable
fun ClassesView(
    modifier: Modifier = Modifier,
    timetables: List<Timetable>,
    isRefreshing: Boolean = false,
    onStarredToggled: (Timetable) -> Unit = {},
    contentBeforeTimeTable: @Composable () -> Unit = {}
) {

    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        item { contentBeforeTimeTable() }

        if(isRefreshing) item {
            Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                Column( horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    CircularProgressIndicator()
                    Text(
                        text = "Refreshing...",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        if (timetables.isEmpty() && !isRefreshing) {
            item {
                Text(
                    text = "No classes found",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else items(timetables) { timetable ->
            ClassEventCard(classEvent = timetable, onStarredToggled = { onStarredToggled(it) })
        }
    }
}


@Composable
fun ClassEventCard(
    modifier: Modifier = Modifier,
    classEvent: Timetable,
    onStarredToggled: (Timetable) -> Unit
) {
    val context = LocalContext.current
    val isDarkTheme = isSystemInDarkTheme()

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraSmall,
        colors = CardDefaults.cardColors(
            generateRandomBrightColor(isDarkTheme)
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                Modifier.padding(12.dp)
            ) {
                Text(
                    text = classEvent.course ?: "Course Name Missing",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(4.dp))

                ClassEventDetailRow(getFormattedTimeString(classEvent.time), Icons.Rounded.Schedule)
                ClassEventDetailRow(classEvent.room, Icons.Rounded.Place)
                ClassEventDetailRow(classEvent.teacher, Icons.Rounded.Person)

            }

            IconButton(onClick = {
                onStarredToggled(classEvent.copy(starred = classEvent.starred!!.not()))
                val toastText = if (classEvent.starred!!) "Removed from starred" else "Added to starred"
                Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    imageVector = if (classEvent.starred!!) Icons.Rounded.Star else Icons.Rounded.StarOutline,
                    contentDescription = "Star",
                )
            }

        }
    }
}

@Composable
internal fun ClassEventDetailRow(detail: String?, icon: ImageVector) {
    detail?.let {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = "$detail")
            Text(
                text = it, style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

internal fun getFormattedTimeString(time: String?): String {
    val secondPart = time?.substring(time.length - 2, time.length) ?: ""
    val firstPart = time?.substring(0, time.length - 2) ?: ""
    return "$firstPart $secondPart"
}


@Preview
@Composable
fun PreviewSingleClassEvent() {
    MaterialTheme {
        ClassEventCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp), classEvent = Timetable(
                id = 1,
                class_ = "BS-VIII (CS)",
                course = "Physics",
                teacher = "MHM",
                room = "Room 101",
                time = "8:00 - 10:00AM",
                day = "Mo",
                starred = true
            )
        ) {}
    }
}