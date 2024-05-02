package com.lumins.sua.views.finance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lumins.sua.android.utils.viewModelFactory
import com.lumins.sua.ui.theme.SUATheme

@Composable
fun FinanceOnboarding(viewModel: FinanceViewModel) {
    var balance by remember { mutableIntStateOf(0) }
    var balanceIsValid by remember { mutableStateOf(false)}
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome to Finance", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(36.dp))
        Column(Modifier.fillMaxWidth()) {
            Text(text = "Your Current Balance", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = "$balance",
                onValueChange = { balance = it.toIntOrNull() ?: 0; balanceIsValid = balance > 0},
                modifier = Modifier.fillMaxWidth(),
                isError = !balanceIsValid,
                label = { Text( if(!balanceIsValid) "Balance should be greater than 0 and a valid number" else "") }
            )
        }

        Spacer(Modifier.height(24.dp))
        OutlinedButton(onClick = {
             if(balanceIsValid) {
                viewModel.financeOnboardingDone(context, balance)
            }
        }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Continue")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                    contentDescription = "continue"
                )
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun PreviewFinanceOnboarding() {
    SUATheme {
        val context = LocalContext.current
        FinanceOnboarding(viewModel = viewModel(factory = viewModelFactory { FinanceViewModel(context) }))
    }
}