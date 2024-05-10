package com.example.fit_nut.util

import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup

fun View.expandWithTransition(
    subView: ViewGroup,
    width: Int = ViewGroup.LayoutParams.MATCH_PARENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
) {
    TransitionManager.beginDelayedTransition(subView)
    val adapter = this.layoutParams
    adapter.width = width
    adapter.height = height
    this.layoutParams = adapter
}

fun View.collapseWithTransition(
    subView: ViewGroup, width: Int = 0, height: Int = 0
) {
    TransitionManager.beginDelayedTransition(subView)
    val adapter = this.layoutParams
    adapter.width = width
    adapter.height = height
    this.layoutParams = adapter
}