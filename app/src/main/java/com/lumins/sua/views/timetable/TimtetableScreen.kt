package com.lumins.sua.views.timetable

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.lumins.sua.android.utils.viewModelFactory
import com.lumins.sua.navigation.SuaScreen
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetableScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val viewModel: TimetableViewModel =
        viewModel(factory = viewModelFactory { TimetableViewModel(context) })
    val backStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(backStackEntry) {
        if(backStackEntry?.destination?.route == SuaScreen.Timetable.route) {
                viewModel.refreshClassData(context)
                Log.d("TAG", "TimetableScreen: LaunchedEffect ran!")
        }
    }

    val timetables by viewModel.timetables.collectAsStateWithLifecycle()
    val classNames by viewModel.classNames.collectAsStateWithLifecycle()
    val selectedClassName by viewModel.selectedClassName.collectAsStateWithLifecycle()
    var selectedIndex by remember { mutableIntStateOf(0) }
    var selectedDate by remember { mutableStateOf<LocalDate>(LocalDate.now()) }
    val filteredTimetables = timetables.filter {
        it.day!!.lowercase() == selectedDate.dayOfWeek.name.lowercase().subSequence(0, 2)
    }

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    var bottomSheetVisible by remember { mutableStateOf(false) }

    Scaffold(Modifier.fillMaxSize()) { paddingValues ->
        AnimatedVisibility(visible = bottomSheetVisible) {
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
                            bottomSheetVisible = false
                        }
                    }
                },
            ) {
                ClassSelection(
                    modifier = Modifier.fillMaxSize(), classNames = classNames
                ) { selectedClassName ->
                    viewModel.onEvent(TimetableEvents.SelectedClassName(selectedClassName))
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (sheetState.isVisible.not()) {
                            bottomSheetVisible = false
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CalendarModeSelect(onSelection = { selected -> selectedIndex = selected })

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {
                    Text(
                        text = "Class", style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = selectedClassName,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    )

                }
                IconButton(modifier = Modifier.size(56.dp), onClick = {
                    scope.launch { sheetState.show() }
                    if (sheetState.isVisible.not()) {
                        bottomSheetVisible = true
                    }
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "Select Class",
                        Modifier.weight(1f)
                    )
                }

            }
            Spacer(modifier = Modifier.height(4.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(top = 4.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${selectedDate.month.name} ${selectedDate.year}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 8.dp)
                )
                AnimatedVisibility(selectedIndex == 0) {
                    WeeklyCalendar(
                        selectedDate = selectedDate
                    ) { selected -> selectedDate = selected }
                }

                Column(modifier = Modifier.fillMaxSize()) {

                    ClassesView(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        timetables = filteredTimetables,
                        onStarredToggled = { viewModel.onEvent(TimetableEvents.StarredToggled(it)) }
                    ) {
                        AnimatedVisibility(selectedIndex == 1) {
                            MonthlyCalendar(
                                modifier = Modifier, selectedDate = selectedDate
                            ) { selected -> selectedDate = selected }
                        }
                    }
                }
            }
        }
    }
}

