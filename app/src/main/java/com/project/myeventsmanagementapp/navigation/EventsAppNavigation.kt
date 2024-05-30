package com.project.myeventsmanagementapp.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.firebase.auth.FirebaseUser
import com.project.myeventsmanagementapp.component.MonthlyHorizontalCalendarView
import com.project.myeventsmanagementapp.screens.auth.AuthViewModel
import com.project.myeventsmanagementapp.screens.auth.LoginScreen
import com.project.myeventsmanagementapp.screens.auth.SignUpScreen
import com.project.myeventsmanagementapp.screens.auth.SplashScreen
import com.project.myeventsmanagementapp.screens.task.AddTagDialog
import com.project.myeventsmanagementapp.screens.task.AddTaskScreen
import com.project.myeventsmanagementapp.screens.task.CategoryScreen
import com.project.myeventsmanagementapp.screens.task.HomeScreen
import com.project.myeventsmanagementapp.screens.task.TaskByDateScreen
import com.project.myeventsmanagementapp.screens.task.TaskViewModel
import com.project.myeventsmanagementapp.screens.task.TasksByCategory

@Composable
fun EventsAppNavigation(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    NavHost(navController = navController,
        startDestination = authViewModel.isSignedIn.value,)
    {
        authNavigation(navController,authViewModel)
        mainAppNavigation(navController, logout = {authViewModel.logout(context)  }) {
            authViewModel.auth.currentUser
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
    logout:() -> Unit,
    userName: () -> FirebaseUser?

){
    navigation(
        startDestination = Screens.MainApp.Home.route,
        route = Screens.MainApp.route
    ){
        composable(Screens.MainApp.Home.route){
            val viewModel: TaskViewModel = hiltViewModel()
            HomeScreen(userName.invoke(),navController,viewModel)
        }
        composable(Screens.MainApp.TaskByDate.route){
                val viewmodel: TaskViewModel = hiltViewModel()
                TaskByDateScreen(viewmodel,navController)

        }
        composable(Screens.MainApp.CategoryScreen.route){
            val taskViewModel:TaskViewModel = hiltViewModel()
            CategoryScreen(userName.invoke(), taskViewModel, navController, logout)

        }
        composable(Screens.MainApp.AddScreen.route){
            val viewModel: TaskViewModel = hiltViewModel()
            viewModel.taskDate.value = it.savedStateHandle.get<String>("selectedDate").orEmpty()
            AddTaskScreen(navController, viewModel )
        }
        composable(Screens.MainApp.StaticsScreen.route){
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)) {}

        }
    }
    dialog(Screens.MainApp.DateDialog.route, dialogProperties = DialogProperties(
        dismissOnClickOutside = true,
        dismissOnBackPress = true
    )){
        MonthlyHorizontalCalendarView(navController){
            navController.popBackStack()
        }
     }
    dialog(
        Screens.MainApp.AddTagDialog.route, dialogProperties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        val taskViewModel: TaskViewModel = hiltViewModel()
        AddTagDialog(navController, taskViewModel)
    }
    composable("${Screens.MainApp.TaskByCategory.route}/{tagName}", arguments = listOf(
        navArgument("tagName"){
            type = NavType.StringType
        }
    )){ navArgument->
        val taskViewModel:TaskViewModel = hiltViewModel()
        val tagWithTaskLists = taskViewModel.tagWithTasks.value.firstOrNull{
            it.tags.name == navArgument.arguments?.getString(
                "tagName"
            ).orEmpty()
        }
        TasksByCategory(tagWithTaskLists, navController,taskViewModel,navArgument.arguments?.getString("tagName"))
    }

}

fun NavOptionsBuilder.popUpToTop(navController: NavController){
    popUpTo(navController.currentBackStackEntry?.destination?.route?: return){
        inclusive =true
        saveState = true
    }
}