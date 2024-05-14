package data.ktor.message

import kotlinx.serialization.Serializable

@Serializable
data class OutputMessage(val message: String? = "")
