package core.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

abstract class BaseStore<T>(getInitialValue: () -> T) {

    private val flow = MutableStateFlow(lazy(getInitialValue))

    val value: T get() = flow.value.value

    fun observe(): Flow<T> = flow.map { it.value }

    fun publish(value: T): Boolean = flow.tryEmit(lazyOf(value))

    fun update(transform: (T) -> T) = flow.update { lazyOf(transform(it.value)) }
}
