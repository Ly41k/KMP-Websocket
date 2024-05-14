package core.exception

interface ExceptionService {
    fun logException(t: Throwable, message: String? = null)
}
