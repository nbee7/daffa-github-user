package com.techtest.daffa_github_user.app

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class NotificationPermissionPolicyTest {
    @Test
    fun `requests notification permission only on first launch on Android 13 or newer when not granted`() {
        assertTrue(shouldRequestNotificationPermission(33, alreadyRequested = false, isGranted = false))
        assertTrue(shouldRequestNotificationPermission(34, alreadyRequested = false, isGranted = false))
        assertFalse(shouldRequestNotificationPermission(32, alreadyRequested = false, isGranted = false))
        assertFalse(shouldRequestNotificationPermission(33, alreadyRequested = true, isGranted = false))
        assertFalse(shouldRequestNotificationPermission(33, alreadyRequested = false, isGranted = true))
    }
}
