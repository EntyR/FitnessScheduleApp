package com.example.myapplication.view.custom

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import com.example.myapplication.R


class TimerView(ctx: Context, attributeSet: AttributeSet) : View(ctx, attributeSet) {


    private var rect: RectF? = null
    private val paint: Paint = Paint().apply {
        color = ctx.getColor(R.color.primary)
    }
    private var mDuration: Long = 110L

    var valueAnimator: ValueAnimator? = null

    var onCompleteCallBack: () -> Unit = {}

    init {

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 15f
        paint.color = ContextCompat.getColor(context, R.color.black)
    }

    val interceptor = false

    private var currentSweepAngle = 0

    fun startSpinnerAnimation(timeMillis: Long) {
        mDuration = timeMillis

        valueAnimator = ValueAnimator.ofInt(0, 360).apply {
            doOnEnd { onCompleteCallBack() }
            duration = mDuration
            interpolator = LinearInterpolator()
            addUpdateListener { valueAnimator ->
                currentSweepAngle = valueAnimator.animatedValue as Int
                invalidate()
            }
        }

        valueAnimator!!.start()
    }

    fun setOnCompleteCallback(callback: () -> Unit) {
        onCompleteCallBack = callback
    }

    fun stopTimer() {
        valueAnimator?.cancel() ?: doOnComplete()
    }

    fun pauseValueAnimator() {
        valueAnimator?.pause() ?: doOnComplete()
    }

    public fun resumeValueAnimator() {
        valueAnimator?.resume() ?: doOnComplete()
    }

    private fun doOnComplete() {

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rect = RectF(
            0 + 10f, 0 + 10f, w.toFloat() - 10, h.toFloat() - 10
        )
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (rect == null) canvas?.let {
            rect = RectF(it.clipBounds)
        }

        rect?.let {
            canvas?.drawArc(
                it, 270f, currentSweepAngle.toFloat(), false, paint
            )


        }
    }
}