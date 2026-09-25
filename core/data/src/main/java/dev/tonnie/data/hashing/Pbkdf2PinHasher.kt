package dev.tonnie.data.hashing

import dev.tonnie.domain.hashing.PinHasher
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class Pbkdf2PinHasher : PinHasher {
    override fun hash(pin: String): String {
        val salt = ByteArray(SALT_LENGTH_BYTES).apply {
            secureRandom.nextBytes(this)
        }

        val hash = generateHash(
                pin = pin,
                salt = salt,
                iterations = ITERATIONS
        )

        val encodedSalt = Base64.getEncoder().encodeToString(salt)
        val encodedHash = Base64.getEncoder().encodeToString(hash)

        return "$ITERATIONS:$encodedSalt:$encodedHash"
    }

    override fun verify(
        pin: String,
        hash: String
    ): Boolean {
        val parts = hash.split(":")

        if (parts.size != 3) {
            return false
        }

        val iterations = parts[0].toIntOrNull()
            ?: return false

        val salt = runCatching {
            Base64.getDecoder()
                    .decode(parts[1])
        }.getOrElse {
            return false
        }

        val expectedHash = runCatching {
            Base64.getDecoder()
                    .decode(parts[2])
        }.getOrElse {
            return false
        }

        val actualHash = generateHash(
                pin = pin,
                salt = salt,
                iterations = iterations
        )

        return MessageDigest.isEqual(
                expectedHash,
                actualHash
        )
    }

    private fun generateHash(
        pin: String,
        salt: ByteArray,
        iterations: Int
    ): ByteArray {
        val spec = PBEKeySpec(
                pin.toCharArray(),
                salt,
                iterations,
                HASH_LENGTH_BITS
        )

        return try {
            SecretKeyFactory
                    .getInstance(ALGORITHM)
                    .generateSecret(spec)
                    .encoded
        } finally {
            spec.clearPassword()
        }
    }

    private companion object {

        const val ALGORITHM = "PBKDF2WithHmacSHA256"
        const val ITERATIONS = 210_000
        const val SALT_LENGTH_BYTES = 16
        const val HASH_LENGTH_BITS = 256
        val secureRandom = SecureRandom()

    }

}

