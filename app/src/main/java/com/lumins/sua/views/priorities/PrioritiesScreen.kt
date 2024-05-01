
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lumins.sua.ui.theme.SUATheme

private val firstTexts = listOf("Issuance of book “Creed III” from LibraryIssuance of book “Creed III” from Library.", "Cash Withdrawl from ATM.", "Book Renewal from University Libbrary.")
private val secondTexts = listOf("Go to “International Scholarship” Seminar in TH.", "Submission of Documents in Administration Block.", "Withdraw OB Course.")
private val thirdTexts = listOf("Completed Task One", "Completed Task Two")
private val allTexts = listOf(Pair("Priorities",firstTexts), Pair("Remind Me", secondTexts), Pair("Discarded", thirdTexts))

@Composable
fun PrioritiesScreen(modifier: Modifier = Modifier.fillMaxSize()) {
    Surface(modifier = modifier.padding(horizontal = 8.dp, vertical = 16.dp)) {
        Column(modifier = Modifier.fillMaxSize()) {

            LazyColumn(Modifier.fillMaxWidth()) {
                items(allTexts) {texts ->
                    
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = texts.first,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        shape = RoundedCornerShape(10.dp),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 12.dp)) {

                            texts.second.forEachIndexed { i, it ->
                                Text(text = it)
                                Spacer(modifier = Modifier.height(4.dp))
                                if(i != texts.second.size - 1) Box(modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth(0.9f)
                                    .background(Color.LightGray)
                                    .align(Alignment.CenterHorizontally)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                            }

                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewPrioritiesScreen() {
    SUATheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            PrioritiesScreen(modifier = Modifier.fillMaxSize())
        }
    }
}