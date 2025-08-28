package work.erlend.securenotesdemo.common.data.local

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

/**
 * Unit tests for [NoteRepository], using a mocked [NoteDao].
 *
 * Ensures that the repository correctly delegates operations to the DAO:
 * - [NoteRepository.addNote] calls DAO insert
 * - [NoteRepository.getAllNotes] retrieves notes from DAO
 * - [NoteRepository.updateNote] calls DAO update
 * - [NoteRepository.deleteNote] calls DAO delete
 */
@OptIn(ExperimentalCoroutinesApi::class)
class NoteRepositoryTest {

    private lateinit var noteDao: NoteDao
    private lateinit var repository: NoteRepository

    @Before
    fun setup() {
        // relaxed = true → no need to stub every single call
        // “Relaxed” means: any function you call on this mock that returns something already has a
        // default value, so you don’t have to explicitly stub it with coEvery.
        // suspend fun insert(note: NoteEntity) →
        // returns Unit (no value), so relaxed = true handles it automatically.
        // suspend fun getAllNotes(): List<NoteEntity> →
        // returns empty list by default.
        // So you can call functions on the mock without preparing them first.
        // This is convenient for functions like insert, update, delete
        // where you don’t care about the return value.
        // getAllNotes → you care about the list returned, so you must stub it with coEvery.
        noteDao = mockk(relaxed = true) // mock of NoteDao
        repository = NoteRepository(noteDao) // real repository instance
    }

    @After
    fun tearDown() {
        clearAllMocks() // reset mock state after each test
    }

    // Call the repository first → triggers DAO calls (mocked).
    // Use coVerify afterward → asserts the calls were made.
    // MockK’s coVerify is passive; it doesn’t execute the function,
    // it just inspects the mock’s history.

    // -----------------------------
    // Insert
    // -----------------------------
    @Test
    fun `insertNote calls dao insert`() = runTest {
        try {
            val note = NoteEntity(content = "Hello")

            // call the repository method, which internally calls the DAO method
            repository.addNote(note)

            // coVerify = verify that a suspend function was called
            // Reads call history to find function call
            coVerify { noteDao.insert(note) }

            println("✅ insertNote test passed")
        } catch (e: Throwable) {
            println("❌ insertNote test failed: ${e.message}")
            throw e
        }
    }

    // -----------------------------
    // Get all notes
    // -----------------------------
    @Test
    fun `getAllNotes returns notes from dao`() = runTest {
        try {
            val note = NoteEntity(id = 1, content = "Test")

            // coEvery = stub the suspend function to return a specific value
            // Some functions do return a value that you care about, like getAllNotes().
            // If you want your test to assert the result,
            // you must stub the return value explicitly.
            // That’s what coEvery does.
            coEvery { noteDao.getAllNotes() } returns listOf(note)

            val result = repository.getAllNotes()

            assertEquals(listOf(note), result)

            // verify the DAO function was actually called
            coVerify { noteDao.getAllNotes() }

            println("✅ getAllNotes test passed")
        } catch (e: Throwable) {
            println("❌ getAllNotes test failed: ${e.message}")
            throw e
        }
    }

    // -----------------------------
    // Update
    // -----------------------------
    @Test
    fun `updateNote calls dao update`() = runTest {
        try {
            val note = NoteEntity(id = 1, content = "A cloudy morning")

            repository.updateNote(1, "A quiet evening")

            // coVerify = check that suspend update was called with correct params
            coVerify { noteDao.update(note.id, "A quiet evening") }

            println("✅ updateNote test passed")
        } catch (e: Throwable) {
            println("❌ updateNote test failed: ${e.message}")
            throw e
        }
    }

    // -----------------------------
    // Delete
    // -----------------------------
    @Test
    fun `deleteNote calls dao delete`() = runTest {
        try {
            val note = NoteEntity(id = 1, content = "Delete me")

            repository.deleteNote(note)

            coVerify { noteDao.delete(note) }

            println("✅ deleteNote test passed")
        } catch (e: Throwable) {
            println("❌ deleteNote test failed: ${e.message}")
            throw e
        }
    }
}