package com.lumins.sua.views.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lumins.sua.android.utils.viewModelFactory
import com.lumins.sua.ui.theme.SUATheme
import com.lumins.sua.views.timetable.ClassSelection
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val viewModel: SettingsViewModel =
        viewModel(factory = viewModelFactory { SettingsViewModel(context) })
    val classNames by viewModel.classNames.collectAsStateWithLifecycle()
    val selectedClassName by viewModel.selectedClassName.collectAsStateWithLifecycle()
    var showBottomSheet by remember { mutableStateOf(false) }
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
                ClassSelection(
                    modifier = Modifier.fillMaxSize(), classNames = classNames
                ) { selectedClassName ->
                    viewModel.onClassSelected(context, selectedClassName)
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (sheetState.isVisible.not()) {
                            showBottomSheet = false
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = "Default Class",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )

                Card(Modifier.fillMaxWidth()) {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = selectedClassName,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(8.dp)
                        )

                        IconButton(
                            onClick = {
                                showBottomSheet = true
                                scope.launch { sheetState.show() }
                            },
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Icon(imageVector = Icons.Rounded.Edit, contentDescription = "edit")
                        }
                    }
                }
            }

            Card(Modifier.fillMaxWidth()) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row {
                        Text(text = "About", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(imageVector = Icons.Outlined.Info, contentDescription = "about-us")
                    }

                    Text(text = "Developed for FYP Project", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)

                    Text(
                        text = "Group Members:\n" +
                                "- Agha Kaleemullah Khan\n" +
                                "- Aizaullah Khan Niazi\n" +
                                "- Asghar Ali Shah",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )

                    Text(text = "V 1.0.0", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Light)
                }
            }

        }
    }

}


@Preview
@Composable
fun SettingsScreenPreview() {
    SUATheme {
        SettingsScreen()
    }
}