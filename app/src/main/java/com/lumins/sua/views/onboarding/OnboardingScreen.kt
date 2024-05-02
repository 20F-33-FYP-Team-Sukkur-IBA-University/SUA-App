package com.lumins.sua.views.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.lumins.sua.R
import com.lumins.sua.android.utils.viewModelFactory
import com.lumins.sua.ui.theme.SUATheme
import com.lumins.sua.views.timetable.ClassSelection
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(onBoardingDone: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val viewModel: OnBoardingViewModel =
        viewModel(factory = viewModelFactory { OnBoardingViewModel(context) })
    val selectedClassName by viewModel.selectedClassName.collectAsStateWithLifecycle()
    val classNames by viewModel.classNames.collectAsStateWithLifecycle()
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val isDarkTheme = isSystemInDarkTheme()
    val lottieComp =
        rememberLottieComposition(spec = LottieCompositionSpec.RawRes(if (isDarkTheme) R.raw.lottie_hi_dark else R.raw.lottie_hi))

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { paddingValues ->
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
            Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            lottieComp.value?.let {
                LottieAnimation(
                    modifier = Modifier.height(400.dp),
                    composition = it,
                    iterations = LottieConstants.IterateForever
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Select your class",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(8.dp))

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

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = {
                    if (selectedClassName != "empty") {
                        viewModel.onBoardingDone(context)
                        onBoardingDone()
                    } else {
                        // show snack bar
                        scope.launch {
                            snackbarHostState.showSnackbar("Please select a class")
                        }
                    }
                }) {
                    Text(text = "Continue")
                }
            }

        }
    }
}


@Preview
@Composable
fun OnboardingScreenPreview() {
    SUATheme {
        OnboardingScreen {}
    }
}