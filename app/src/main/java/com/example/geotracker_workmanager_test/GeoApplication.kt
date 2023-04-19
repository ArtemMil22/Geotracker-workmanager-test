package com.example.geotracker_workmanager_test

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader

class GeoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)
        val client:FlipperClient = AndroidFlipperClient.getInstance(this)
        client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
        client.addPlugin(DatabasesFlipperPlugin(this))
        client.start()
    }
}