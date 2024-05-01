package com.lumins.sua.views.finance

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lumins.sua.navigation.SuaScreen
import com.lumins.sua.android.utils.formatWithCommas
import com.lumins.sua.ui.theme.SUATheme
import com.lumins.sua.ui.theme.SuaColors
import com.lumins.sua.views.finance.components.FinanceReportCard
import com.lumins.sua.views.finance.components.charts.FinanceMainChart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceScreen(viewModel: FinanceViewModel = viewModel(), navigateTo : (String) -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())) {

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)) {
            Text(
                text = "Balance",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.offset(y = 18.dp)
            )
            Text(
                text = 18500.formatWithCommas(),
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }

        FinanceMainChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .offset(y = (-18).dp),
            chartEntryModelProducer = viewModel.multiDataSetChartEntryModelProducer,
            expenseCategories = viewModel.expenseCategories.toList()
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(horizontal = 8.dp, vertical = 12.dp),
            colors = CardDefaults.cardColors(containerColor = SuaColors.darkGreyBackground),
            shape = RoundedCornerShape(50.dp),
            onClick = { /*TODO*/ }
        ) {
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Add Expense",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.offset(y = 2.dp)
                )

                Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowForward, contentDescription = null, tint = Color.White)
            }
        }

        FinanceReportCard(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            //onWeekChipClick
            navigateTo(SuaScreen.SingleWeekFinance.route)

        }
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewFinanceScreen() {
    SUATheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FinanceScreen {}
        }
    }
}