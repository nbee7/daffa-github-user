package com.techtest.daffa_github_user.core

import androidx.test.platform.app.InstrumentationRegistry
import java.io.InputStreamReader

object JsonReader {
    fun readJsonFile(filename: String): String {
        val context = InstrumentationRegistry.getInstrumentation().context
        val inputStream = context.assets.open(filename)
        val reader = InputStreamReader(inputStream)
        return reader.readText()
    }
}
