package work.erlend.securenotesdemo.notes

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.*
import work.erlend.securenotesdemo.MainActivity

class NotesScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun navigateToUpgradeAndBack() {
        // Start on NotesScreen (MainActivity default)

        // Click button to open Upgrade screen
        composeTestRule.onNodeWithText("Open Upgrade Screen (Demo)").performClick()

        // Verify Upgrade screen loaded by finding button
        composeTestRule.onNodeWithText("Show / Generate Passphrase")
            .assertIsDisplayed()
            .performClick()

        // Go back to NotesScreen
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        // Verify we’re back on NotesScreen by checking that the "Open Upgrade Screen" button is visible again
        composeTestRule.onNodeWithText("Open Upgrade Screen (Demo)")
            .assertIsDisplayed()
    }

}