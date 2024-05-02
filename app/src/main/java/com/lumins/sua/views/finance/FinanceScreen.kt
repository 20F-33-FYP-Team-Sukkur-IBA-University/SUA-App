package com.lumins.sua.views.finance

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lumins.sua.android.utils.formatWithCommas
import com.lumins.sua.android.utils.viewModelFactory
import com.lumins.sua.navigation.SuaScreen
import com.lumins.sua.ui.theme.SUATheme
import com.lumins.sua.ui.theme.SuaColors
import com.lumins.sua.views.finance.components.AddExpense
import com.lumins.sua.views.finance.components.FinanceReportCard
import com.lumins.sua.views.finance.components.charts.EditBalance
import com.lumins.sua.views.finance.components.charts.FinanceMainChart
import kotlinx.coroutines.launch

@Composable
fun FinanceScreen(navigateTo : (String) -> Unit) {
    val context = LocalContext.current
    val viewModel: FinanceViewModel = viewModel(factory = viewModelFactory { FinanceViewModel(context) })
    val startScreen by viewModel.startScreen.collectAsStateWithLifecycle()

    when (startScreen) {
        SuaScreen.Finance.route -> {
            MainFinanceScreen(viewModel, navigateTo)
        }
        SuaScreen.Loading.route -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Loading...")
                }
            }
        }
        else -> {
            FinanceOnboarding(viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainFinanceScreen(viewModel: FinanceViewModel, navigateTo: (String) -> Unit) {
    val balance by viewModel.balance.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val expenses by viewModel.expenses.collectAsStateWithLifecycle()
    val lineChartData by viewModel.lineChartData.collectAsStateWithLifecycle()
    val todayAmount by viewModel.todayAmount.collectAsStateWithLifecycle()
    val weekAmount by viewModel.weekAmount.collectAsStateWithLifecycle()
    val monthAmount by viewModel.monthAmount.collectAsStateWithLifecycle()

    LaunchedEffect(expenses) {
        viewModel.updateExpenseChart()
        viewModel.updateExpenseReport()

    }

    var showBottomSheet by remember { mutableStateOf(false) }
    var isAddNewExpense by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    Scaffold { paddingValues ->
        AnimatedVisibility(visible = showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                sheetState = sheetState,
                tonalElevation = 8.dp,
                properties = ModalBottomSheetProperties(
                    shouldDismissOnBackPress = true,
                    isFocusable = true,
                    securePolicy = SecureFlagPolicy.Inherit
                ),
                onDismissRequest = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (sheetState.isVisible.not()) {
                            showBottomSheet = false
                        }
                    }
                },
            ) {
               if(isAddNewExpense) AddExpense {

                   viewModel.addExpense(context, it)
                   scope.launch { sheetState.hide() }.invokeOnCompletion {
                       if (sheetState.isVisible.not()) {
                           showBottomSheet = false
                       }
                   }
               }
                else EditBalance(balance) {
                    viewModel.updateBalance(context, it)
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (sheetState.isVisible.not()) {
                            showBottomSheet = false
                        }
                    }

               }
            }
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
        ) {

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)) {
                Text(
                    text = "Balance",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.offset(y = 18.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = balance.formatWithCommas(),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold,
                    )

                    IconButton(onClick = { isAddNewExpense = false; showBottomSheet = true }) {
                        Icon(imageVector = Icons.Rounded.Edit, contentDescription = null)

                    }
                }
            }

            FinanceMainChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(10.dp)),
                lineChartData = lineChartData
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                colors = CardDefaults.cardColors(containerColor = SuaColors.darkGreyBackground),
                shape = RoundedCornerShape(50.dp),
                onClick = {
                    isAddNewExpense = true
                    showBottomSheet = true
                }
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
                    .padding(horizontal = 8.dp),
                expenses = expenses,
                todayAmount = todayAmount,
                weekAmount = weekAmount,
                monthAmount = monthAmount
            ) {
                navigateTo("${SuaScreen.SingleWeekFinance.route}/${it.name}")

            }
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