package com.project.myeventsmanagementapp.ui

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.testing.TestNavHostController
import com.project.myeventsmanagementapp.MainActivity
import com.project.myeventsmanagementapp.component.BottomBar
import com.project.myeventsmanagementapp.navigation.EventsAppNavigation
import com.project.myeventsmanagementapp.navigation.Screens
import com.project.myeventsmanagementapp.screens.auth.AuthViewModel
import com.project.myeventsmanagementapp.ui.theme.MyEventsManagementAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class TestTabNavigation {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var authViewModel: AuthViewModel
    private lateinit var navController: TestNavHostController

    @Before
    fun init(){
        hiltRule.inject()
        composeTestRule.activity.setContent {
            authViewModel = hiltViewModel()
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(DialogNavigator())
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            MyEventsManagementAppTheme {
                EventsAppNavigation(authViewModel, navController)
                BottomBar(navController )
            }
        }
    }

    @Test
    fun testBottomBarNavigation(){
        composeTestRule.onNodeWithTag("Navigate To Add Screen").assertIsDisplayed().performClick()
        navController.assertCurrentRouteName(Screens.MainApp.AddScreen.route)

        composeTestRule.onNodeWithTag(Screens.MainApp.TaskByDate.route).assertIsDisplayed().performClick()
        navController.assertCurrentRouteName(Screens.MainApp.TaskByDate.route)

        composeTestRule.onNodeWithTag(Screens.MainApp.Home.route).assertIsDisplayed().performClick()
        navController.assertCurrentRouteName(Screens.MainApp.Home.route)

        composeTestRule.onNodeWithTag(Screens.MainApp.CategoryScreen.route).assertIsDisplayed().performClick()
        navController.assertCurrentRouteName(Screens.MainApp.CategoryScreen.route)

        composeTestRule.onNodeWithTag(Screens.MainApp.StaticsScreen.route).assertIsDisplayed().performClick()
        navController.assertCurrentRouteName(Screens.MainApp.StaticsScreen.route)

    }
}

fun NavController.assertCurrentRouteName(expectedRouteName:String){
    TestCase.assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
}