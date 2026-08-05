package com.system.traffic.navigation

import androidx.navigation3.runtime.NavKey

class Navigator(val state: NavigationState) {

    fun navigate(route: NavKey) {
        if(route in state.backStacks.keys) {
            state.topLevelRoute = route
        } else {
            state.backStacks[state.topLevelRoute]?.add(route)
        }
    }

    fun goBack() {
        val currentStack = state.backStacks[state.topLevelRoute]
            ?: error("Back stack for ${state.topLevelRoute} doesn't exist")
        if (currentStack.isEmpty()) return

        val currentRoute = currentStack.last()

        if (currentRoute == state.topLevelRoute) {
            if (state.excludeStartRouteFromStacks) return
            state.topLevelRoute = state.startRoute
        } else {
            currentStack.removeLastOrNull()
        }
    }

    fun navigateWithRemoveCurrentRoute(route: NavKey) {
        val currentTopLevel = state.topLevelRoute
        val currentStack = state.backStacks[currentTopLevel]
            ?: error("Back stack for $currentTopLevel doesn't exist")

        if (route in state.backStacks.keys) {
            currentStack.clear()
            if (currentTopLevel == state.startRoute && route != state.startRoute) {
                state.excludeStartRouteFromStacks = true
            }
            state.topLevelRoute = route
        } else {
            currentStack.removeLastOrNull()
            currentStack.add(route)
        }
    }
}