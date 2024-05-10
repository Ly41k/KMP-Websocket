package data.ktor

import kotlinx.serialization.Serializable

@Serializable
data class OutputMessage(val message: String? = "")
