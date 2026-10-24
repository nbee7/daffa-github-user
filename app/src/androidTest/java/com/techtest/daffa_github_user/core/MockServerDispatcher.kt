package com.techtest.daffa_github_user.core

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class MockServerDispatcher {
    class RequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val path = request.path ?: ""
            return when {
                path.contains("/search/users") -> {
                    MockResponse()
                        .setResponseCode(200)
                        .setBody(JsonReader.readJsonFile("search_response.json"))
                }
                path.contains("/users/octocat/followers") -> {
                    MockResponse()
                        .setResponseCode(200)
                        .setBody(JsonReader.readJsonFile("users_response.json"))
                }
                path.contains("/users/octocat/following") -> {
                    MockResponse()
                        .setResponseCode(200)
                        .setBody(JsonReader.readJsonFile("users_response.json"))
                }
                path.contains("/users/octocat") -> {
                    MockResponse()
                        .setResponseCode(200)
                        .setBody(JsonReader.readJsonFile("detail_user_response.json"))
                }
                path.contains("/users") -> {
                    MockResponse()
                        .setResponseCode(200)
                        .setBody(JsonReader.readJsonFile("users_response.json"))
                }
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    class ErrorDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return MockResponse().setResponseCode(500)
        }
    }
}
