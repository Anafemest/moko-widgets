/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.widgets.factory

import dev.icerock.moko.mvvm.State
import dev.icerock.moko.widgets.StatefulWidget
import dev.icerock.moko.widgets.core.ViewBundle
import dev.icerock.moko.widgets.core.ViewFactoryContext
import dev.icerock.moko.widgets.style.view.WidgetSize
import dev.icerock.moko.widgets.utils.Edges
import dev.icerock.moko.widgets.utils.applyBackground
import dev.icerock.moko.widgets.utils.fillChildView
import dev.icerock.moko.widgets.utils.applySizeToChild
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGFloat
import platform.CoreGraphics.CGRectZero
import platform.UIKit.UIView
import platform.UIKit.UIViewController
import platform.UIKit.addSubview
import platform.UIKit.hidden
import platform.UIKit.translatesAutoresizingMaskIntoConstraints

actual class DefaultStatefulWidgetViewFactory actual constructor(
    style: Style
) : DefaultStatefulWidgetViewFactoryBase(style) {

    override fun <WS : WidgetSize> build(
        widget: StatefulWidget<out WidgetSize, *, *>,
        size: WS,
        viewFactoryContext: ViewFactoryContext
    ): ViewBundle<WS> {
        val viewController: UIViewController = viewFactoryContext

        val container = UIView(frame = CGRectZero.readValue()).apply {
            translatesAutoresizingMaskIntoConstraints = false
            applyBackground(style.background)
        }

        listOf(
            widget.dataWidget,
            widget.emptyWidget,
            widget.loadingWidget,
            widget.errorWidget
        ).forEach { childWidget ->
            val childViewBundle = childWidget.buildView(viewController)
            val childView = childViewBundle.view
            childView.translatesAutoresizingMaskIntoConstraints = false

            with(container) {
                addSubview(childView)

                val edges: Edges<CGFloat> = applySizeToChild(
                    rootPadding = style.padding,
                    rootView = container,
                    childView = childView,
                    childSize = childViewBundle.size,
                    childMargins = childViewBundle.margins
                )

                fillChildView(childView, edges)
            }

            fun updateState(state: State<*, *>) {
                childView.hidden = when (state) {
                    is State.Data -> childWidget == widget.dataWidget
                    is State.Empty -> childWidget == widget.emptyWidget
                    is State.Error -> childWidget == widget.errorWidget
                    is State.Loading -> childWidget == widget.loadingWidget
                }.not()
            }

            updateState(widget.state.value)
            widget.state.addObserver { updateState(it) }
        }

        return ViewBundle(
            view = container,
            size = size,
            margins = style.margins
        )
    }
}
