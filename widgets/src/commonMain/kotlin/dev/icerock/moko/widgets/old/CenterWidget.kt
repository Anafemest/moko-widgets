package dev.icerock.moko.widgets.old

import dev.icerock.moko.widgets.core.VFC
import dev.icerock.moko.widgets.core.View
import dev.icerock.moko.widgets.core.ViewFactoryContext
import dev.icerock.moko.widgets.core.Widget

expect var centerWidgetViewFactory: VFC<CenterWidget>

class CenterWidget(
    private val factory: VFC<CenterWidget>,
    val child: Widget
) : Widget() {

    constructor(
        factory: VFC<CenterWidget>,
        childFactory: () -> Widget
    ) : this(
        factory = factory,
        child = childFactory()
    )

    override fun buildView(viewFactoryContext: ViewFactoryContext): View =
        factory(viewFactoryContext, this)
}