package com.unifit.unifit.presentation.ui.utils

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams

object EdgeToEdgeHelper {
    fun updateMarginToStatusBarInsets(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars())
            // Apply the insets as a margin to the view. This solution sets
            // only the bottom, left, and right dimensions, but you can apply whichever
            // insets are appropriate to your layout. You can also update the view padding
            // if that's more appropriate.
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = insets.top
                Log.d("TAG", "updateMarginToStatusBarInsets: $topMargin")
            }
            WindowInsetsCompat.CONSUMED
        }
    }

    fun updateMarginToSystemBarsInsets(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            /*v.updatePadding(
                top = insets.top,
                bottom = insets.bottom
            )*/
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin = insets.bottom
            }
            /*v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin = insets.bottom + v.marginBottom
                topMargin = insets.top + v.marginTop
                Log.d("TAG", "updateMarginToNavigationBarInsets: $bottomMargin")
            }*/
            WindowInsetsCompat.CONSUMED
        }
    }

}