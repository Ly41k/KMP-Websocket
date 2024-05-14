package data.ktor.message

import kotlinx.serialization.Serializable

@Serializable
data class InputMessage(val message: String)
