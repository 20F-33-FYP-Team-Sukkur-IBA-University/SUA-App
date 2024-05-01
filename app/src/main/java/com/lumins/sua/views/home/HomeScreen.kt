package com.lumins.sua.views.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.lumins.sua.R
import com.lumins.sua.navigation.SuaScreen
import com.lumins.sua.ui.theme.SUATheme

@Composable
fun HomeScreen(navigateTo: (String) -> Unit) {
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {

            Row(Modifier.zIndex(1f)) {
                Spacer(Modifier.width(24.dp))
                Image(
                    painter = painterResource(id = R.drawable.philosopher_hd),
                    contentDescription = null,
                    modifier = Modifier
                        .height(120.dp)
                        .width(86.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 50.dp,
                                topEnd = 5.dp,
                                bottomStart = 5.dp,
                                bottomEnd = 5.dp
                            )
                        ),
                    contentScale = ContentScale.FillHeight,

                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 56.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {

                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Marcus Aurelius",
                        modifier = Modifier,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "55–135 C.E.",
                        modifier = Modifier.align(Alignment.End),
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "\"You have power over your mind — not outside events. Realize this, and you will find strength.\"",
                        modifier = Modifier,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        lineHeight = 32.sp
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                DestinationCard(
                    modifier = Modifier
                        .width(180.dp),
                    icon = R.drawable.ic_monitoring,
                    title = "Financials"
                ) { navigateTo(SuaScreen.Finance.route) }
                DestinationCard(
                    modifier = Modifier
                        .width(180.dp),
                    icon = R.drawable.ic_robot,
                    title = "AI"
                ) { navigateTo(SuaScreen.ChatBot.route) }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                DestinationCard(
                    modifier = Modifier
                        .width(180.dp),
                    icon = R.drawable.ic_timetable,
                    title = "Timetable"
                ) { navigateTo(SuaScreen.Timetable.route) }
                DestinationCard(
                    modifier = Modifier
                        .width(180.dp),
                    icon = R.drawable.ic_email,
                    title = "Emails"
                ) { navigateTo(SuaScreen.EmailAlerts.route) }
            }
        }

    }

}

@Composable
private fun DestinationCard(modifier: Modifier = Modifier, icon: Int, title: String, onClick: () -> Unit) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        onClick = onClick
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement =
            Arrangement.SpaceEvenly
        ) {
            Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
            Icon(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(68.dp)
            )
        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewHomeScreen() {
    SUATheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreen {

            }
        }
    }
}