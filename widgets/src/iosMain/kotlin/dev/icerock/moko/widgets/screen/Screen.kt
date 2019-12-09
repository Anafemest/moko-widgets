/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.widgets.screen

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import platform.UIKit.UIViewController

actual abstract class Screen<Arg : Args> {
    val viewModelStore = mutableMapOf<Any, ViewModel>()
    // TODO private?
    var arg: Arg? = null
    // TODO private?
    var parent: Screen<*>? = null

    actual inline fun <reified VM : ViewModel, Key : Any> getViewModel(
        key: Key,
        crossinline viewModelFactory: () -> VM
    ): VM {
        val stored = viewModelStore[key]
        if (stored != null) return stored as VM

        val created = viewModelFactory()
        viewModelStore[key] = created
        return created
    }

    actual fun <T : Any> createEventsDispatcher(): EventsDispatcher<T> {
        return EventsDispatcher()
    }

    abstract fun createViewController(): UIViewController

    actual val parentScreen: Screen<*>? get() = parent
}
