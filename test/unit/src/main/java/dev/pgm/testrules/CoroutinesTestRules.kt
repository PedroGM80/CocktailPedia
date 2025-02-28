package dev.pgm.testrules

import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher

class CoroutinesTestRules : TestWatcher() {

    val testDispatcher = StandardTestDispatcher()

    override fun starting(description: Description) {
        super.starting(description)

    }

    override fun finished(description: Description) {
        super.finished(description)

    }
}