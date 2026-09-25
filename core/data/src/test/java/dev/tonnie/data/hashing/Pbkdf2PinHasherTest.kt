package dev.tonnie.data.hashing

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

class Pbkdf2PinHasherTest {

    private val pinHasher = Pbkdf2PinHasher()

    @Test
    fun `hashing same pin twice produces different hashes`() {

        val pin = "12345"

        val firstHash = pinHasher.hash(pin)
        val secondHash = pinHasher.hash(pin)

        firstHash shouldNotBe secondHash
    }

    @Test
    fun `verify returns true for correct pin`() {
        val hash = pinHasher.hash("12345")
        val result = pinHasher.verify(
                pin = "12345",
                hash = hash
        )
        result.shouldBeTrue()
    }

    @Test
    fun `verify returns false for incorrect pin`() {
        val hash = pinHasher.hash("12345")

        val result = pinHasher.verify(
                pin = "00000",
                hash = hash
        )
        result.shouldBeFalse()
    }
}