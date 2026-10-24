package com.techtest.daffa_github_user.core

import com.techtest.daffa_github_user.core.data.local.room.UserDatabase
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okhttp3.mockwebserver.SocketPolicy
import org.junit.rules.ExternalResource
import org.koin.core.context.GlobalContext
class MockServerRule(private val dispatcher: Dispatcher) : ExternalResource() {

    private val mockWebServer = MockWebServer()

    override fun before() {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse =
                dispatcher.dispatch(request).apply {
                    setSocketPolicy(SocketPolicy.DISCONNECT_AT_END)
                }
        }
        mockWebServer.start(PORT)
        clearAppCache()
    }

    override fun after() {
        mockWebServer.shutdown()
        clearAppCache()
    }
    private fun clearAppCache() {
        val database: UserDatabase = GlobalContext.get().get(UserDatabase::class)
        database.clearAllTables()
    }

    companion object {
        const val PORT = 8080
    }
}
