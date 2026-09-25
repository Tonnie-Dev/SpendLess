package dev.tonnie.domain.hashing

interface PinHasher {
    fun hash(pin: String): String
    fun verify(pin: String, hash: String): Boolean
}