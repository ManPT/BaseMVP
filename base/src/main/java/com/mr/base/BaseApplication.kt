package com.mr.base

import android.app.Application
import android.content.Context
import com.mr.base.tools.Tool

class BaseApplication : Application() {
    companion object {
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        Tool.init(this)
    }

}