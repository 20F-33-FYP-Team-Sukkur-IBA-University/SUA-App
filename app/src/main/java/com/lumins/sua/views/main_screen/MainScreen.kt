package com.lumins.sua.views.main_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lumins.sua.android.utils.viewModelFactory
import com.lumins.sua.navigation.SuaNavHost
import com.lumins.sua.navigation.SuaScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val viewModel: MainViewModel = viewModel(factory = viewModelFactory { MainViewModel(context) })
    val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    var topAppBarTitle by remember { mutableStateOf("Timetable") }
    var showTopAppBarNavigationButton by remember { mutableStateOf(false) }
    var showBottomNavigation by remember { mutableStateOf(false) }
    var showTopBar by remember { mutableStateOf(true) }
    var currentDestination by remember { mutableStateOf("") }

    val items = listOf(
        SuaScreen.Timetable, SuaScreen.Finance, SuaScreen.ChatBot, SuaScreen.EmailAlerts
    )



    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect {
            currentDestination = it.destination.route ?: ""
            when (it.destination.route) {
                SuaScreen.Finance.route -> {
                    topAppBarTitle = "Financials"; showTopAppBarNavigationButton = false
                    showBottomNavigation = true; showTopBar = true
                }

                SuaScreen.SingleWeekFinance.route -> {
                    topAppBarTitle = "This Week's Financials"; showTopAppBarNavigationButton = true
                    showBottomNavigation = false; showTopBar = true
                }

                SuaScreen.Timetable.route -> {
                    topAppBarTitle = "Timetable"; showTopAppBarNavigationButton = false
                    showBottomNavigation = true; showTopBar = true
                }

                SuaScreen.ChatBot.route -> {
                    topAppBarTitle = "ChatBot"; showTopAppBarNavigationButton = false
                    showBottomNavigation = true; showTopBar = true
                }

                SuaScreen.EmailAlerts.route -> {
                    topAppBarTitle = "Email Alerts"; showTopAppBarNavigationButton = false
                    showBottomNavigation = true; showTopBar = true
                }

                SuaScreen.Settings.route -> {
                    topAppBarTitle = "Settings"; showTopAppBarNavigationButton = true
                    showBottomNavigation = false; showTopBar = true
                }

                else -> {
                    topAppBarTitle = "Unknown Screen"; showTopAppBarNavigationButton = false
                    showBottomNavigation = false; showTopBar = false
                }
            }
        }
    }

    Scaffold(topBar = {
        if (showTopBar) TopAppBar(
            title = { Text(text = topAppBarTitle) },
            navigationIcon = {
                if (showTopAppBarNavigationButton) Icon(imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable {
                            navController.popBackStack()
                        })
            },
            actions = {
                AnimatedVisibility(visible = (currentDestination == SuaScreen.Timetable.route)) {
                    IconButton(
                        onClick = { navController.navigate(SuaScreen.Settings.route) },
                        Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(imageVector = Icons.Rounded.Settings, contentDescription = null)
                    }
                }
            }
        )
    }, bottomBar = {


        if (showBottomNavigation) NavigationBar {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            items.forEach { screen ->
                NavigationBarItem(icon = {
                    Icon(
                        screen.icon,
                        contentDescription = null
                    )
                },
                    label = {
                        Text(
                            stringResource(screen.resourceId),
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // re selecting the same item
                            launchSingleTop = true
                            // Restore state when re selecting a previously selected item
                            restoreState = true
                        }
                    })
            }
        }
    }) {
        SuaNavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = startDestination
        )
    }
}
