package core.store

abstract class ClearableBaseStore<T>(
    getInitialValue: () -> T,
    private val getClearValue: () -> T
) : BaseStore<T>(getInitialValue) {
    fun clear(): Boolean = publish(getClearValue())
}
