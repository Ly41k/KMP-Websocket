package core.exception

import io.ktor.client.network.sockets.SocketTimeoutException

class DefaultExceptionService : ExceptionService {

    override fun logException(t: Throwable, message: String?) {
        if (t is SocketTimeoutException) return
        println("$message - $t")
    }
}
