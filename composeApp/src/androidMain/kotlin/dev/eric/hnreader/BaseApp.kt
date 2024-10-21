package dev.eric.hnreader

import KoinInit
import android.app.Application


class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInit(applicationContext).init()
    }
}