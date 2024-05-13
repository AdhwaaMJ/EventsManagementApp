package com.project.myeventsmanagementapp.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.project.myeventsmanagementapp.screens.auth.AuthViewModel
import com.project.myeventsmanagementapp.screens.auth.LoginScreen
import com.project.myeventsmanagementapp.screens.auth.SignUpScreen
import com.project.myeventsmanagementapp.screens.auth.SplashScreen

@Composable
fun EventsAppNavigation(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    NavHost(navController = navController,
        startDestination = authViewModel.isSignedIn.value,)
    {
        authNavigation(navController,authViewModel)
        mainAppNavigation(navController){
//            authViewModel.logout()
        }

    }
}

fun NavGraphBuilder.authNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel
){
    navigation(
        startDestination = Screens.Authentication.Splash.route,
        route = Screens.Authentication.route,
    ){
        composable(Screens.Authentication.Splash.route){
            SplashScreen(navController)

        }
        composable(Screens.Authentication.SignUp.route){
            SignUpScreen(navController,authViewModel)


        }
        composable(Screens.Authentication.Login.route){
            LoginScreen(navController,authViewModel)

        }
    }
}

fun NavGraphBuilder.mainAppNavigation(
    navController: NavHostController,
    logout:() -> Unit
){
    navigation(
        startDestination = Screens.MainApp.Home.route,
        route = Screens.MainApp.route
    ){
        composable(Screens.MainApp.Home.route){
            Column(modifier = Modifier.fillMaxSize().background(Color.Cyan)) {}
        }
        composable(Screens.MainApp.TaskByDate.route){
            Column(modifier = Modifier.fillMaxSize().background(Color.Blue)) {}
        }

        composable(Screens.MainApp.CategoryScreen.route){
            Column(modifier = Modifier.fillMaxSize().background(Color.Red)) {
                Button(onClick = {
                    logout.invoke()
                }) {
                    Text(text = "SingOut")

                }
            }
        }

        composable(Screens.MainApp.AddScreen.route){
            Column(modifier = Modifier.fillMaxSize().background(Color.Green)) {}


        }
        composable(Screens.MainApp.StaticsScreen.route){
            Column(modifier = Modifier.fillMaxSize().background(Color.Gray)) {}

        }

    }
}

fun NavOptionsBuilder.popUpToTop(navController: NavController){
    popUpTo(navController.currentBackStackEntry?.destination?.route?: return){
        inclusive =true
        saveState = true
    }
}