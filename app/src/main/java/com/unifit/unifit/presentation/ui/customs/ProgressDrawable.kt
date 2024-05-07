package com.unifit.unifit.presentation.ui.customs

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.unifit.unifit.R


internal class ProgressDrawable(private val currentLevel: Int, private val context: Context) : Drawable() {
    companion object {
        private const val NUM_RECTS = 3
    }
    private var mPaint = Paint()
    override fun onLevelChange(level: Int): Boolean {
        invalidateSelf()
        return true
    }

    override fun draw(canvas: Canvas) {
        val b = bounds
        val width = b.width().toFloat()
        val rectWidth = width / NUM_RECTS
        val progressWidth = currentLevel.toFloat() / 10 * width
        //TODO MAKE IT PRETTIER
        for (i in 0 until NUM_RECTS) {
            val left = rectWidth * i
            val right = if(i == NUM_RECTS-1) left + rectWidth else left + 0.9f * rectWidth
            mPaint.color = if (right <= progressWidth) ContextCompat.getColor(context, R.color.green_deep) else ContextCompat.getColor(context, R.color.green_light)
            canvas.drawRect(left, b.top.toFloat(), right, b.bottom.toFloat(), mPaint)
        }
    }

    override fun setAlpha(alpha: Int) {}
    override fun setColorFilter(cf: ColorFilter?) {}
    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }
}