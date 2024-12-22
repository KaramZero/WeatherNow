package com.vodafone.core.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

enum class TransitionDirection {
    INWARDS,
    OUTWARDS
}

fun scaleIntoContainer(
    direction: TransitionDirection = TransitionDirection.INWARDS,
    initialScale: Float = if (direction == TransitionDirection.OUTWARDS) 0.9f else 1.1f
) = scaleIn(
    animationSpec = tween(220, delayMillis = 90),
    initialScale = initialScale
) + fadeIn(animationSpec = tween(220, delayMillis = 90))

fun scaleOutOfContainer(
    direction: TransitionDirection = TransitionDirection.OUTWARDS,
    targetScale: Float = if (direction == TransitionDirection.INWARDS) 0.9f else 1.1f
) = scaleOut(
    animationSpec = tween(
        durationMillis = 220,
        delayMillis = 90
    ), targetScale = targetScale
) + fadeOut(tween(delayMillis = 90))


fun NavGraphBuilder.composableWithAnimation(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit)
) {
    composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        enterTransition = {
            scaleIntoContainer(direction = TransitionDirection.OUTWARDS)
        },
        exitTransition = {
            scaleOutOfContainer(direction = TransitionDirection.OUTWARDS)
        },
        popEnterTransition = {
            scaleIntoContainer(direction = TransitionDirection.INWARDS)
        },
        popExitTransition = {
            scaleOutOfContainer(direction = TransitionDirection.INWARDS)
        },
        content = content
    )
}