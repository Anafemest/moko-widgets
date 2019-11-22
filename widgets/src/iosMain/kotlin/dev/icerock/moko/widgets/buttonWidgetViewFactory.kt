/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.widgets

import dev.icerock.moko.graphics.toUIColor
import dev.icerock.moko.widgets.core.VFC
import dev.icerock.moko.widgets.core.bind
import dev.icerock.moko.widgets.utils.applyBackground
import dev.icerock.moko.widgets.utils.applyTextStyle
import dev.icerock.moko.widgets.utils.setEventHandler
import dev.icerock.moko.widgets.utils.UIStyledButton
import platform.UIKit.UIButton
import platform.UIKit.UIButtonTypeRoundedRect
import platform.UIKit.UIControlEventTouchUpInside
import platform.UIKit.UIControlStateNormal
import platform.UIKit.UILayoutConstraintAxisHorizontal
import platform.UIKit.UILayoutConstraintAxisVertical
import platform.UIKit.UILayoutPriorityRequired
import platform.UIKit.setContentHuggingPriority
import platform.UIKit.translatesAutoresizingMaskIntoConstraints

actual var buttonWidgetViewFactory: VFC<ButtonWidget> = { viewController, widget ->
    // TODO add styles support
    val style = widget.style


    val button = UIStyledButton().apply {
        translatesAutoresizingMaskIntoConstraints = false
        setContentHuggingPriority(UILayoutPriorityRequired, forAxis = UILayoutConstraintAxisHorizontal)
        setContentHuggingPriority(UILayoutPriorityRequired, forAxis = UILayoutConstraintAxisVertical)
        applyStateBackground(style.background)
        
        titleLabel?.applyTextStyle(style.textStyle)

        style.textStyle.color?.also {
            setTintColor(it.toUIColor())
        }
    }


    widget.text.bind {
        button.setTitle(title = it.localized(), forState = UIControlStateNormal)
    }

    button.setEventHandler(UIControlEventTouchUpInside) {
        widget.onTap()
    }

    button
}
