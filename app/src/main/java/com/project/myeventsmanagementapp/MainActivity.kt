package com.project.myeventsmanagementapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.myeventsmanagementapp.navigation.EventsAppNavigation
import com.project.myeventsmanagementapp.navigation.Screens
import com.project.myeventsmanagementapp.screens.auth.AuthViewModel
import com.project.myeventsmanagementapp.component.BottomBar
import com.project.myeventsmanagementapp.ui.theme.MyEventsManagementAppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.google.firebase.FirebaseApp


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            val authViewModel: AuthViewModel = hiltViewModel()
            MyEventsManagementAppTheme {

                val navController = rememberNavController()
                val config = LocalConfiguration.current
                var showBottomBar by rememberSaveable { mutableStateOf(false) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                showBottomBar = when (navBackStackEntry?.destination?.route) {
                    Screens.MainApp.Home.route -> true
                    Screens.MainApp.AddScreen.route -> true
                    Screens.MainApp.TaskByDate.route -> true
                    Screens.MainApp.CategoryScreen.route -> true
                    Screens.MainApp.StaticsScreen.route -> true
                    else -> false
                }
                CompositionLocalProvider(
                    LocalLayoutDirection provides
                            if (config.layoutDirection == LayoutDirection.Rtl.ordinal)
                                LayoutDirection.Rtl
                            else LayoutDirection.Ltr
                ){

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .semantics { contentDescription = "MyScreen" }
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)

                    ) {
                        if (authViewModel.error.value.isNotEmpty()) {
                            Snackbar(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                containerColor = Color.Red.copy(0.5f)
                            ) {
                                Text(text = authViewModel.error.value)

                            }
                        }
                        EventsAppNavigation(authViewModel, navController)
                    }
                    if (showBottomBar) {
                        BottomBar(navController)
                    }
                }
              }
            }
        }
    }
}





