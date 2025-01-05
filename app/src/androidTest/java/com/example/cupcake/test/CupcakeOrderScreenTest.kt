package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.cupcake.data.DataSource
import com.example.cupcake.ui.SelectOptionScreen
import org.junit.Rule
import org.junit.Test
import com.example.cupcake.R
import com.example.cupcake.data.OrderUiState
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.StartOrderScreen
import org.hamcrest.Matchers.contains

class CupcakeOrderScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    val mockOrderUiState = OrderUiState(
        quantity = 999999,
        flavor = "Vanilla",
        date = getFormattedDate(),
        price = "$100",
        pickupOptions = listOf()
    )
    @Test
    fun startScreen_verifyContentDisplay() {
        composeTestRule.setContent {
            StartOrderScreen(
                quantityOptions = DataSource.quantityOptions,
                onNextButtonClicked = {}
            )
        }
        composeTestRule.onNodeWithStringId(R.string.order_cupcakes).assertExists()

        DataSource.quantityOptions.forEach { pair ->
            composeTestRule.onNodeWithStringId(pair.first).assertExists()
        }
    }

    @Test
    fun selectOptionScreen_verifyContentDisplay() {
        /* Question: is it a good practice to get real app 'flavors' from DataSource.flavors,
        or is it better to use some mock data, like:
        'val mockFlavors = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")'?*/
        val flavors = getFlavorStringListFromStringResourcesList()
        val mockSubtotal = "$100"

        // When SelectOptionScreen is loaded...
        composeTestRule.setContent {
            SelectOptionScreen(subtotal = mockSubtotal, options = flavors)
        }
        // Then all the options are displayed on the screen.
        flavors.forEach { flavor ->
            composeTestRule.onNodeWithText(flavor).assertIsDisplayed()
        }
        // And then the subtotal is displayed correctly.
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.subtotal_price, mockSubtotal)
        ).assertIsDisplayed()

        // And then the next button is disabled
        composeTestRule.onNodeWithStringId(R.string.next).assertIsNotEnabled()
    }

    //Verify that the Next button is enabled when an option is selected on the Choose Flavor screen.
    @Test
    fun selectOptionScreen_optionSelected_NextButtonEnabled() {
        /* Question: is the naming of 'mockFlavors' and 'mockSubtotal' good? Or it can be better?*/
        val mockFlavors = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")
        val mockSubtotal = "$100"

        /* Question: is it ok that I have duplicated code ('mockSubtotal', 'composeTestRule.setContent{...}')
        in this function and in the function above? Or it's better to refactor? */
        composeTestRule.setContent {
            SelectOptionScreen(subtotal = mockSubtotal, options = mockFlavors)
        }
        composeTestRule.onNodeWithText("Vanilla").performClick()
        composeTestRule.onNodeWithStringId(R.string.next).assertIsEnabled()
    }

    @Test
    fun orderSummaryScreen_verifyContentDisplay() {
        composeTestRule.setContent {
            OrderSummaryScreen(
                orderUiState = mockOrderUiState,
                onCancelButtonClicked = {},
                onSendButtonClicked = { _, _ -> }
            )
        }
        // attempts to test the 'quantity' that failed:
        // composeTestRule.onNodeWithText(mockOrderUiState.quantity.toString()).assertIsDisplayed()
        // composeTestRule.onNode(hasText(mockOrderUiState.quantity.toString(), ignoreCase = true)).assertIsDisplayed()
        // composeTestRule.onNode(hasText(contains(mockOrderUiState.quantity.toString()))).assertIsDisplayed()

        composeTestRule.onNodeWithText(
            composeTestRule.activity.resources.getQuantityString(
                R.plurals.cupcakes,
                mockOrderUiState.quantity,
                mockOrderUiState.quantity
            )
        ).assertIsDisplayed()

        composeTestRule.onNodeWithText(mockOrderUiState.flavor).assertIsDisplayed()
        composeTestRule.onNodeWithText(mockOrderUiState.date).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.subtotal_price, mockOrderUiState.price)
        ).assertIsDisplayed()
    }


    private fun getFlavorStringListFromStringResourcesList(): List<String> {
        val flavorsAsString = mutableListOf<String>()
        for (item in DataSource.flavors) { // 'flavors' is imported from the app DataSource
            val string = composeTestRule.activity.getString(item)
            flavorsAsString.add(string)
        }
        return flavorsAsString
    }

}
