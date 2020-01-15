/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.icerockdev.library.universal

import com.icerockdev.library.MR
import dev.icerock.moko.fields.FormField
import dev.icerock.moko.fields.liveBlock
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.*
import dev.icerock.moko.widgets.core.Image
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.core.Value
import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.NavigationBar
import dev.icerock.moko.widgets.screen.NavigationItem
import dev.icerock.moko.widgets.screen.WidgetScreen
import dev.icerock.moko.widgets.screen.getParentScreen
import dev.icerock.moko.widgets.screen.getViewModel
import dev.icerock.moko.widgets.screen.listen
import dev.icerock.moko.widgets.style.input.InputType
import dev.icerock.moko.widgets.style.view.SizeSpec
import dev.icerock.moko.widgets.style.view.WidgetSize

class LoginScreen(
    private val theme: Theme,
    private val loginViewModelFactory: (EventsDispatcher<LoginViewModel.EventsListener>) -> LoginViewModel
) : WidgetScreen<Args.Empty>(), NavigationItem, LoginViewModel.EventsListener {

    override val navigationBar: NavigationBar = NavigationBar.None

    override val isKeyboardResizeContent: Boolean = true
    override val isDismissKeyboardOnTap: Boolean = true

    override fun createContentWidget() = with(theme) {
        val viewModel = getViewModel {
            loginViewModelFactory(createEventsDispatcher())
        }
        viewModel.eventsDispatcher.listen(this@LoginScreen, this@LoginScreen)

        constraint(size = WidgetSize.AsParent) {
            val toolBar = +container(
                size = WidgetSize.Const(SizeSpec.AsParent, SizeSpec.Exact(60f)),
                id=Id.ToolBarBg
            ) {}

            val titleToolBar = +text(
                id = Id.TitleToolBar,
                size = WidgetSize.WrapContent,
                text = const("Авторизация")
            )

            val skipButton = +button(
                id = Id.SkipButtonId,
                size = WidgetSize.WrapContent,
                content = ButtonWidget.Content.Text(Value.data("Регистрация".desc())),
                onTap = viewModel::onRegistrationPressed
            )

            val phoneInput = +input(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                id = Id.PhoneInputId,
                label = const("Номер телефона".desc() as StringDesc),
                field = viewModel.phoneField,
                inputType = InputType.PHONE
            )
            val passwordInput = +input(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                id = Id.PasswordInputId,
                label = const("Пароль".desc() as StringDesc),
                field = viewModel.passwordField,
                inputType = InputType.PLAIN_TEXT
            )
            val loginButton = +button(
                size = WidgetSize.Const(SizeSpec.AsParent, SizeSpec.Exact(50f)),
                content = ButtonWidget.Content.Text(Value.data("Войти".desc())),
                onTap = viewModel::onLoginPressed
            )

            val registerButton = +button(
                id = Id.RegistrationButtonId,
                size = WidgetSize.Const(SizeSpec.AsParent, SizeSpec.Exact(50f)),
                content = ButtonWidget.Content.Text(Value.data("Продолжить без авторизации".desc())),
                onTap = viewModel::onRegistrationPressed
            )

            constraints {
                toolBar topToTop root
                toolBar leftRightToLeftRight root

                titleToolBar bottomToBottom toolBar
                titleToolBar leftToLeft toolBar

                skipButton bottomToBottom toolBar
                skipButton rightToRight toolBar

                phoneInput topToBottom toolBar offset 32
                phoneInput leftRightToLeftRight root offset 16

                passwordInput topToBottom phoneInput offset 24
                passwordInput leftRightToLeftRight root offset 16

                loginButton bottomToTop registerButton offset 16
                loginButton leftRightToLeftRight root

                registerButton bottomToBottom root.safeArea offset 16
                loginButton leftRightToLeftRight root

            }
        }
    }



//    override fun createContentWidget() = with(theme) {
//        val viewModel = getViewModel {
//            loginViewModelFactory(createEventsDispatcher())
//        }
//        viewModel.eventsDispatcher.listen(this@LoginScreen, this@LoginScreen)
//
//        constraint(size = WidgetSize.AsParent) {
//            val toolBar = +container(
//                size = WidgetSize.Const(SizeSpec.AsParent, SizeSpec.Exact(60f)),
//                id=Id.ToolBarBg
//            ) {}
//
//            val titleToolBar = +text(
//                id = Id.TitleToolBar,
//                size = WidgetSize.WrapContent,
//                text = const("Authorization")
//            )
//
//            val phoneInput = +input(
//                size = WidgetSize.WidthAsParentHeightWrapContent,
//                id = Id.PhoneInputId,
//                label = const("Phone".desc() as StringDesc),
//                field = viewModel.phoneField,
//                inputType = InputType.PHONE
//            )
//            val passwordInput = +input(
//                size = WidgetSize.WidthAsParentHeightWrapContent,
//                id = Id.PasswordInputId,
//                label = const("Password".desc() as StringDesc),
//                field = viewModel.passwordField,
//                inputType = InputType.PLAIN_TEXT
//            )
//            val loginButton = +button(
//                size = WidgetSize.Const(SizeSpec.AsParent, SizeSpec.Exact(50f)),
//                content = ButtonWidget.Content.Text(Value.data("Login".desc())),
//                onTap = viewModel::onLoginPressed
//            )
//
//            val registerButton = +button(
//                id = Id.RegistrationButtonId,
//                size = WidgetSize.Const(SizeSpec.AsParent, SizeSpec.Exact(50f)),
//                content = ButtonWidget.Content.Text(Value.data("Registration".desc())),
//                onTap = viewModel::onRegistrationPressed
//            )
//
//            val skipButton = +button(
//                id = Id.SkipButtonId,
//                size = WidgetSize.WrapContent,
//                content = ButtonWidget.Content.Text(Value.data("SKIP".desc())),
//                onTap = viewModel::onRegistrationPressed
//            )
//
//                        constraints {
//                toolBar topToTop root
//                toolBar leftRightToLeftRight root
//
//                titleToolBar centerYToCenterY toolBar
//                titleToolBar leftToLeft toolBar
//
//                skipButton centerYToCenterY toolBar
//                skipButton rightToRight toolBar
//
//                phoneInput topToBottom toolBar offset 32
//                phoneInput leftRightToLeftRight root offset 16
//
//                passwordInput topToBottom phoneInput offset 24
//                passwordInput leftRightToLeftRight root offset 16
//
//                loginButton bottomToTop registerButton offset 16
//                loginButton leftRightToLeftRight root
//
//                registerButton bottomToBottom root.safeArea offset 16
//                loginButton leftRightToLeftRight root
//
//            }
//        }
//    }

    object Id {
        object PhoneInputId : InputWidget.Id
        object PasswordInputId : InputWidget.Id
        object RegistrationButtonId : ButtonWidget.Id
        object SkipButtonId : ButtonWidget.Id
        object ToolBarBg : ContainerWidget.Id
        object TitleToolBar : TextWidget.Id
    }

    interface Parent {
        fun routeToMain()
    }

    override fun routeToMain() {
        getParentScreen<Parent>().routeToMain()
    }
}

class LoginViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>
) : ViewModel(), EventsDispatcherOwner<LoginViewModel.EventsListener> {
    val phoneField = FormField<String, StringDesc>("", liveBlock { null })
    val passwordField = FormField<String, StringDesc>("", liveBlock { "blablabla".desc() })

    fun onLoginPressed() {
    }

    fun onRegistrationPressed() {}

    interface EventsListener {
        fun routeToMain()
    }
}
