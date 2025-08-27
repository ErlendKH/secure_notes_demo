package work.erlend.securenotesdemo.theory

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test
import work.erlend.securenotesdemo.MainActivity

class TheoryScreenTest {

    // Use an Android activity for instrumented Compose tests
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    // Tests navigation to Agile screen was successful
    @Test
    fun navigatesToAgileScreenDoesNotFindText() {
        // Click the "Theory" button
        composeTestRule.onNodeWithText("Theory").performClick()
        // Click the "Agile" button
        composeTestRule.onNodeWithText("Agile").performClick()
        // assert previous screen button is gone
        composeTestRule.onNodeWithText("Agile").assertDoesNotExist()
    }

    // Also tests that navigation to Agile screen was successful
    @Test
    fun navigatesToAgileScreenFindText() {
        // Click the "Theory" button
        composeTestRule.onNodeWithText("Theory").performClick()
        // Click the "Agile" button
        composeTestRule.onNodeWithText("Agile").performClick()
        // Assert that a node in the Agile screen is displayed
        composeTestRule.onNodeWithText("Agile Info").assertIsDisplayed()
    }

    // Tests that navigation back to Theory screen was successful
    @Test
    fun navigatesToAgileScreenNavigateBack() {
        // Click the "Theory" button
        composeTestRule.onNodeWithText("Theory").performClick()
        // Click the "Agile" button
        composeTestRule.onNodeWithText("Agile").performClick()
        // Click the "Back" button
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        // Assert that a node in the Agile screen is displayed
        composeTestRule.onNodeWithText("Agile").assertIsDisplayed()
    }

}