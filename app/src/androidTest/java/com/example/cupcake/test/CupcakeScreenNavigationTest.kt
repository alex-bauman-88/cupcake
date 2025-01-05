package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.cupcake.CupcakeApp
import com.example.cupcake.CupcakeScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.example.cupcake.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CupcakeScreenNavigationTest {
    // Rules establish a specific order and control over the test environment.
    // They dictate when to set up resources, execute the test, and clean up afterward.
    // E.G.: ActivityScenarioRule: This rule launches a specific activity before each test and
    // terminates it after the test completes. It's useful for testing the behavior
    // of a single activity in isolation.
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupCupcakeNavHost(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            CupcakeApp(navController = navController)
        }
    }

    @Test
    fun cupcakeNavHost_verifyStartDestination(){
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    @Test
    fun cupcakeNavHost_verifyBackNavigationNotShownOnStartOrderScreen() {
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }
    @Test
    fun cupcakeNavHost_clickOneCupcake_navigatesToSelectFlavorScreen(){
        composeTestRule.onNodeWithStringId(R.string.one_cupcake)
            .performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
    }

    // DEL the comment: Navigating to Start screen by clicking Up button from Flavor screen
    @Test
    fun cupcakeNavHost_clickUpOnFlavorScreen_navigatesToStartScreen(){
        navigateToFlavorScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    // DEL the comment: Navigating to Start screen by clicking Cancel button from Flavor screen
    @Test
    fun cupcakeNavHost_clickCancelOnFlavorScreen_navigatesToStartScreen(){
        navigateToFlavorScreen()
        performOnCancelButtonClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }
    // DEL the comment: Navigating to the Pickup screen
    @Test
    fun cupcakeNavHost_navigatingToPickupScreen_navigatesToPickupScreen(){
        navigateToPickupDateScreen()
        navController.assertCurrentRouteName(CupcakeScreen.Pickup.name)
    }
    // DEL the comment: Navigating to Flavor screen by clicking Up button from Pickup screen
    @Test
    fun cupcakeNavHost_clickUpOnPickupScreen_navigatesToFlavorScreen(){
        navigateToPickupDateScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
    }
    // DEL the comment: Navigating to Start screen by clicking Cancel button from Pickup screen
    @Test
    fun cupcakeNavHost_clickingCancelOnPickupScreen_navigatesToStartScreen(){
        navigateToPickupDateScreen()
        performOnCancelButtonClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }
    // DEL the comment: Navigating to the Summary screen
    @Test
    fun cupcakeNavHost_navigatingToSummaryScreen_navigatesToSummaryScreen(){
        navigateToSummaryScreen()
        navController.assertCurrentRouteName(CupcakeScreen.Summary.name)
    }

    // DEL the comment: Navigating to Start screen by clicking Cancel button from Summary screen
    @Test
    fun cupcakeNavHost_clickingCancelOnSummaryScreen_navigatesToStartScreen(){
        navigateToSummaryScreen()
        performOnCancelButtonClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    private fun navigateToFlavorScreen(){
        composeTestRule.onNodeWithStringId(R.string.one_cupcake)
            .performClick()
    }
    private fun navigateToPickupDateScreen(){
        navigateToFlavorScreen()
        composeTestRule.onNodeWithStringId(R.string.chocolate)
            .performClick()
        composeTestRule.onNodeWithStringId(R.string.next)
            .performClick()
    }
    private fun navigateToSummaryScreen(){
        navigateToPickupDateScreen()
        composeTestRule.onNodeWithText(getFormattedDate())
            .performClick()
        composeTestRule.onNodeWithStringId(R.string.next)
            .performClick()
    }
    private fun performNavigateUp(){
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText)
            .performClick()
    }
    private fun performOnCancelButtonClick() {
        composeTestRule.onNodeWithStringId(R.string.cancel)
            .performClick()
    }



}