/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

object Versions {
    object Android {
        const val compileSdk = 28
        const val targetSdk = 28
        const val minSdk = 21
    }

    const val kotlin = "1.3.50"

    object Libs {
        object Android {
            const val appCompat = "1.0.2"
            const val material = "1.0.0"
            const val constraintLayout = "1.1.3"
            const val lifecycle = "2.0.0"
            const val recyclerView = "1.0.0"
            const val inputMask = "5.0.0"
            const val glide = "4.10.0"
        }

        object MultiPlatform {
            const val coroutines = "1.3.0"

            const val mokoWidgets = "0.1.0"
            const val mokoResources = "0.4.0"
            const val mokoMvvm = "0.3.0"
            const val mokoFields = "0.1.0"
            const val mokoUnits = "0.1.1"
            const val mokoMedia = "0.1.0"
        }
    }
}