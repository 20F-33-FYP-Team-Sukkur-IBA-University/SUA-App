package com.lumins.sua.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Chat
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Today
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.lumins.sua.R
import com.lumins.sua.android.views.finance.SingleWeekFinanceScreen
import com.lumins.sua.views.chatbot.ChatBotScreen
import com.lumins.sua.views.email_alerts.EmailAlertsScreen
import com.lumins.sua.views.finance.FinanceScreen
import com.lumins.sua.views.onboarding.OnboardingScreen
import com.lumins.sua.views.settings.SettingsScreen
import com.lumins.sua.views.timetable.TimetableScreen

@Composable
fun SuaNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        composable(route = SuaScreen.Finance.route) {
            FinanceScreen {
                //navigateTo
                navController.navigate(it)
            }
        }

        composable(route = SuaScreen.Onboarding.route) {
            OnboardingScreen {
            }
        }

        composable(route = SuaScreen.Loading.route) {
            val lottieCompo by rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(R.raw.lottie_loading)
            )
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if(lottieCompo != null) {
                    LottieAnimation(
                        composition = lottieCompo,
                        iterations = LottieConstants.IterateForever
                    )
                } else {
                    Column {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Loading...")
                    }
                }
            }
        }

        composable(route = SuaScreen.SingleWeekFinance.route) {
            SingleWeekFinanceScreen()
        }

        composable(route = SuaScreen.Timetable.route) {
            TimetableScreen(navController = navController)
        }

        composable(route = SuaScreen.ChatBot.route) {
            ChatBotScreen()
        }

        composable(route = SuaScreen.EmailAlerts.route) {
            EmailAlertsScreen()
        }

        composable(route = SuaScreen.Settings.route) {
            SettingsScreen()
        }
    }
}

sealed class SuaScreen(val route: String, val resourceId: Int, val icon: ImageVector) {
    data object Finance : SuaScreen("finance", R.string.finance_title, Icons.Rounded.AttachMoney)
    data object SingleWeekFinance :
        SuaScreen("single_week", R.string.finance_title, Icons.Rounded.AttachMoney)

    data object Timetable : SuaScreen("timetable", R.string.timetable_title, Icons.Rounded.Today)
    data object ChatBot :
        SuaScreen("chat-bot", R.string.chatbot_title, Icons.AutoMirrored.Rounded.Chat)

    data object EmailAlerts : SuaScreen("email-alerts", R.string.alerts_title, Icons.Rounded.Email)
    data object Settings : SuaScreen("sua-settings", R.string.alerts_title, Icons.Rounded.Settings)
    data object Onboarding : SuaScreen("onboarding", R.string.alerts_title, Icons.Rounded.Settings)
    data object Loading : SuaScreen("loading", R.string.alerts_title, Icons.Rounded.Settings)
}