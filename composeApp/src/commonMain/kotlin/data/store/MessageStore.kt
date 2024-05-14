package data.store

import core.store.ClearableBaseStore
import domain.modes.presentation.message.MessageItem

class MessageStore : ClearableBaseStore<List<MessageItem>>(
    getClearValue = { emptyList() },
    getInitialValue = { emptyList() }
)
