package com.example.baseandroid.extensions

import android.animation.Animator
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup

fun View.beInvisibleIf(beInvisible: Boolean) = if (beInvisible) beInvisible() else beVisible()

fun View.beVisibleOrInvisibleIf(beVisible: Boolean) = if (beVisible) beVisible() else beInvisible()

fun View.beVisibleIf(beVisible: Boolean) = if (beVisible) beVisible() else beGone()

fun View.beGoneIf(beGone: Boolean) = beVisibleIf(!beGone)

fun View.beInvisible() {
    visibility = View.INVISIBLE
}

fun View.beVisible() {
    visibility = View.VISIBLE
}

fun View.beGone() {
    visibility = View.GONE
}

fun View.isVisible() = visibility == View.VISIBLE

fun View.isInvisible() = visibility == View.INVISIBLE

fun View.isGone() = visibility == View.GONE

fun View.disappear(duration: Long) {
    animate().alpha(0f).setDuration(duration).setListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(p0: Animator?) {
        }

        override fun onAnimationEnd(p0: Animator?) {
            beGone()
        }

        override fun onAnimationCancel(p0: Animator?) {
        }

        override fun onAnimationStart(p0: Animator?) {
        }
    })
}

fun View.appear(duration: Long) {
    animate().alpha(1f).setDuration(duration).setListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(p0: Animator?) {
        }

        override fun onAnimationEnd(p0: Animator?) {

        }

        override fun onAnimationCancel(p0: Animator?) {
        }

        override fun onAnimationStart(p0: Animator?) {
            beVisible()
        }
    })
}

fun View.listenSoftKeyboard(callback: (() -> Unit)? = null) {
    this.viewTreeObserver.addOnGlobalLayoutListener {
        val r = Rect()
        getWindowVisibleDisplayFrame(r)
        val heightDiff: Int = rootView.height - r.bottom
        if (heightDiff < rootView.height * 0.15) {
            callback?.invoke()
        }
    }
}

fun ViewGroup.getLastChild(): View? {
    return if (childCount > 0) getChildAt(childCount-1) else null
}


