package work.erlend.securenotesdemo.common.data.security

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

/** Generates a random AES key for use in tests. */
fun generateTestKey(): SecretKey {
    val keyGen = KeyGenerator.getInstance("AES")
    keyGen.init(256) // or 128/192 depending on your implementation
    return keyGen.generateKey()
}

/**
 * Unit tests for [KeystorePassphraseManager] encryption and decryption functions.
 *
 * Verifies that encrypting and then decrypting returns the original data for:
 * - Normal strings
 * - Empty strings
 * - Unicode strings
 * - Long strings
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class KeystorePassphraseManagerTest {

    private lateinit var testKey: SecretKey

    @Before
    fun setup() {
        testKey = generateTestKey() // // Generate a test AES key
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    // -----------------------------
    // encrypt / decrypt
    // -----------------------------
    @Test
    fun `encrypt followed by decrypt returns original data`() = runTest {
        try {
            val data = "Secret data"

            val key = generateTestKey()
            val encrypted = KeystorePassphraseManager.encrypt(data, key)
            val decrypted = KeystorePassphraseManager.decrypt(encrypted, key)
            assertEquals(data, decrypted)

            println("✅ encrypt/decrypt test passed")
        } catch (e: Throwable) {
            println("❌ encrypt/decrypt test failed: ${e.message}")
            throw e
        }
    }

    // -----------------------------
    // Empty string
    // -----------------------------
    @Test
    fun `encrypt and decrypt empty string`() {
        val data = ""
        try {
            val encrypted = KeystorePassphraseManager.encrypt(data, testKey)
            val decrypted = KeystorePassphraseManager.decrypt(encrypted, testKey)

            // AES-GCM (or any symmetric encryption) operates on byte arrays.
            // An empty string is just a zero-length byte array.
            // Encryption still produces a non-empty ciphertext,
            // because the IV and GCM tag are added.
            // Decryption then reconstructs the original byte array (length zero) →
            // converts back to an empty string.
            assertEquals(data, decrypted)
            println("Empty string test passed: '$decrypted'")
        } catch (e: Throwable) {
            println("Empty string test failed: ${e.message}")
            throw e
        }
    }

    // -----------------------------
    // Unicode string
    // -----------------------------
    @Test
    fun `encrypt and decrypt unicode string`() {
        val data = "😀🌍🚀 — Привет — こんにちは — مرحبا"
        try {
            val encrypted = KeystorePassphraseManager.encrypt(data, testKey)
            val decrypted = KeystorePassphraseManager.decrypt(encrypted, testKey)

            // Encryption works on bytes, and UTF-8 encoding can represent all Unicode characters.
            // The only requirement is to encode the string to bytes consistently
            // before encryption and decode the bytes back after decryption.
            // Your implementation uses passphrase.toByteArray(Charsets.UTF_8) →
            // works for emojis, accented letters, non-Latin scripts.
            assertEquals(data, decrypted)
            println("Unicode string test passed: '$decrypted'")
        } catch (e: Throwable) {
            println("Unicode string test failed: ${e.message}")
            throw e
        }
    }

    // -----------------------------
    // Long string
    // -----------------------------
    @Test
    fun `encrypt and decrypt long string`() {
        val data = "A".repeat(10_000)
        try {
            val encrypted = KeystorePassphraseManager.encrypt(data, testKey)
            val decrypted = KeystorePassphraseManager.decrypt(encrypted, testKey)

            // AES-GCM works on any length of data,
            // internally handling it in blocks (128-bit blocks for AES).
            // Even very long strings (e.g., 10,000 characters)
            // are converted to bytes and encrypted without problems.
            // Decryption just reverses the process block by block.
            assertEquals(data, decrypted)
            println("Long string test passed: length = ${decrypted.length}")
        } catch (e: Throwable) {
            println("Long string test failed: ${e.message}")
            throw e
        }
    }

}