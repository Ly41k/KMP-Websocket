package presentation.chats.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Serializable
data class ChatNavData(
    val id: Int,
    val title: String
) {
    fun getSerializedData(json: Json): String = json.encodeToString(this)
}
