package com.example.highlevelarch

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * APPLICATION CLASS
 * Required to trigger Hilt's code generation for dependency injection.
 */
@HiltAndroidApp
class HighLevelArchApp : Application()
