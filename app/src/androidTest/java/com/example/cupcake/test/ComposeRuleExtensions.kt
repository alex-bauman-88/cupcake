package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule

// '<A : ComponentActivity>' is Upper Bound Constraint
// https://medium.com/@ramadan123sayed/kotlin-generics-the-ultimate-guide-with-practical-examples-ca3f5ca557e7
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithStringId(
    @StringRes id: Int
): SemanticsNodeInteraction = onNodeWithText(activity.getString(id))


//Without onNodeWithStringId:
//
//You’d need to manually resolve the string resource every time:
//
//val text = activity.getString(R.string.some_text)
//composeTestRule.onNodeWithText(text).assertExists()
//
//With onNodeWithStringId:
//
//You can simplify the process:
//
//composeTestRule.onNodeWithStringId(R.string.some_text).assertExists()
//
//This is cleaner and easier to maintain, especially for apps with multiple locales.
