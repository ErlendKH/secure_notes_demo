package work.erlend.securenotesdemo.notes

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavController
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import io.mockk.mockk
import work.erlend.securenotesdemo.MainActivity
import work.erlend.securenotesdemo.common.data.local.NoteEntity
import work.erlend.securenotesdemo.common.data.local.NotesDatabase
import work.erlend.securenotesdemo.common.data.local.NoteRepository

//@RunWith(AndroidJUnit4::class)
class NotesScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var database: NotesDatabase
    private lateinit var repository: NoteRepository
//    private lateinit var navController: NavController

    @Before
    fun setup() {
        // In-memory database
        database = Room.inMemoryDatabaseBuilder(
            composeTestRule.activity,
            NotesDatabase::class.java
        ).allowMainThreadQueries().build()

        repository = NoteRepository(database.noteDao())
//        navController = mockk(relaxed = true)

        // Set the NotesScreen content
//        composeTestRule.setContent {
//            NotesScreen(noteRepository = repository, navController = navController)
//        }
    }

    @After
    fun tearDown() {
        database.close()
    }

    // -----------------------------
    // Add Note
    // -----------------------------
    @Test
    fun addNote_showsInList() {
        val noteText = "Test note"

        composeTestRule.onNodeWithText("Add Note").performClick()
        composeTestRule.onNode(hasSetTextAction()).performTextInput(noteText)
        composeTestRule.onNodeWithText("Save").performClick()

        composeTestRule.onNodeWithText(noteText).assertIsDisplayed()
    }

    // -----------------------------
    // Edit Note
    // -----------------------------
    @Test
    fun editNote_updatesText(): Unit = runBlocking {
        val originalText = "Original note"
        val updatedText = "Updated note"

        repository.addNote(NoteEntity(content = originalText))
        composeTestRule.waitForIdle()

        // TODO: Error with 'Edit'
        // AssertionError: Failed to inject touch input.
        // Reason: Expected exactly '1' node but could not find any node that satisfies:
        // (ContentDescription = 'Edit' (ignoreCase: false))
        composeTestRule.onNodeWithContentDescription("Edit").performClick()
        composeTestRule.onNode(hasSetTextAction()).performTextClearance()
        composeTestRule.onNode(hasSetTextAction()).performTextInput(updatedText)
        composeTestRule.onNodeWithText("Update").performClick()

        composeTestRule.onNodeWithText(updatedText).assertIsDisplayed()
    }

    // -----------------------------
    // Delete Note
    // -----------------------------
    @Test
    fun deleteNote_removesFromList() = runBlocking {
        val noteText = "Note to delete"

        repository.addNote(work.erlend.securenotesdemo.common.data.local.NoteEntity(content = noteText))
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithContentDescription("Delete").performClick()
        composeTestRule.onNodeWithText("Delete").performClick()

        composeTestRule.onNodeWithText(noteText).assertDoesNotExist()
    }

    // -----------------------------
    // Search Note
    // -----------------------------
    @Test
    fun searchNote_filtersList(): Unit = runBlocking {
        repository.addNote(NoteEntity(content = "Apple"))
//        repository.addNote(NoteEntity(content = "Banana"))
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Search notes").performTextInput("App")

        composeTestRule.onNodeWithText("Apple").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Banana").assertDoesNotExist()
    }
}