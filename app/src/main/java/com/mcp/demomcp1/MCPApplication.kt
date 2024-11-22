package com.mcp.demomcp1
import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.evergage.android.ClientConfiguration
import com.evergage.android.Evergage
import com.evergage.android.LogLevel
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MCPApplication : Application(), Application.ActivityLifecycleCallbacks {
    lateinit var currentActivity : Activity

    companion object {
        lateinit var instance: MCPApplication
            private set
    }
    override fun onCreate() {
        super.onCreate()
        instance = this

        registerActivityLifecycleCallbacks(this)

        // Initialize Evergage:
        Evergage.initialize(this)
        Evergage.setLogLevel(LogLevel.DEBUG)
        val evergage = Evergage.getInstance()

        evergage.userId = getString(R.string.userId)

        FirebaseMessaging.getInstance().token.addOnSuccessListener { token: String? ->
            Evergage.getInstance().setFirebaseToken(token)
        }

        // Start Evergage with your Evergage Configuration:
        evergage.start(
            ClientConfiguration.Builder()
                .account(getString(R.string.mcp_instance))
                .dataset(getString(R.string.dataset))
                .usePushNotifications(true)
                .build()
        )
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        currentActivity = p0
    }

    override fun onActivityStarted(p0: Activity) {
        currentActivity = p0
    }

    override fun onActivityResumed(p0: Activity) {
        currentActivity = p0
    }

    override fun onActivityPaused(p0: Activity) {
        currentActivity = p0
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        currentActivity = p0
    }

    override fun onActivityStopped(p0: Activity) {

    }

    override fun onActivityDestroyed(p0: Activity) {

    }
}