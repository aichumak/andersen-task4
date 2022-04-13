package com.example.lassicclockapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

class ClassicClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var seconds = START_VALUE
    private var minutes = START_VALUE
    private var hours = START_VALUE
    private var hourLineLength = HOUR_LINE_LENGTH
    private var minuteLineLength = MINUTE_LINE_LENGTH
    private var secondLineLength = SECOND_LINE_LENGTH
    private var hourLineWidth = HOUR_LINE_WIDTH
    private var minuteLineWidth = MINUTE_LINE_WIDTH
    private var secondLineWidth = SECOND_LINE_WIDTH
    private var hourLineColor = HOUR_LINE_DEFAULT_COLOR
    private var minuteLineColor = MINUTE_LINE_DEFAULT_COLOR
    private var secondLineColor = SECOND_LINE_DEFAULT_COLOR

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ClassicClockView,
            0, 0
        ).apply {
            try {
                hourLineColor = getColor(
                    R.styleable.ClassicClockView_hourHandColor, HOUR_LINE_DEFAULT_COLOR
                )
                minuteLineColor = getColor(
                    R.styleable.ClassicClockView_minuteHandColor, MINUTE_LINE_DEFAULT_COLOR
                )
                secondLineColor = getColor(
                    R.styleable.ClassicClockView_secondHandColor, SECOND_LINE_DEFAULT_COLOR
                )
                hourLineWidth = getFloat(
                    R.styleable.ClassicClockView_hourHandWidthSize, HOUR_LINE_WIDTH
                )
                minuteLineWidth = getFloat(
                    R.styleable.ClassicClockView_minuteHandWidthSize, MINUTE_LINE_WIDTH
                )
                secondLineWidth = getFloat(
                    R.styleable.ClassicClockView_secondHandWidthSize, SECOND_LINE_WIDTH
                )
                hourLineLength = getFloat(
                    R.styleable.ClassicClockView_hourHandLengthSize, HOUR_LINE_LENGTH
                )
                minuteLineLength = getFloat(
                    R.styleable.ClassicClockView_minuteHandLengthSize, MINUTE_LINE_LENGTH
                )
                secondLineLength = getFloat(
                    R.styleable.ClassicClockView_secondHandLengthSize, SECOND_LINE_LENGTH
                )
            } finally {
                recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            val radius = getCorrectRadius()

            it.scale(0.5f * radius, -0.5f * radius)
            it.translate(width / radius, -height / radius)

            paint.color = Color.BLACK
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = CLOCK_STROKE_WIDTH

            //Clock dial
            for (i in 1..MAX_VALUE_360) {
                val x1 = (cos(Math.PI * 2 - STEP_IN_360 * i)).toFloat()
                val y1 = (sin(Math.PI * 2 - STEP_IN_360 * i)).toFloat()
                it.drawPoint(x1, y1, paint)
            }

            for (i in 1..MAX_VALUE_12) {
                val x1 = (cos(Math.PI * 2 - STEP_IN_12 * i)).toFloat()
                val y1 = (sin(Math.PI * 2 - STEP_IN_12 * i)).toFloat()
                val x2 = x1 * CLOCK_RADIUS_LINE_SCALE
                val y2 = y1 * CLOCK_RADIUS_LINE_SCALE
                it.drawLine(x1, y1, x2, y2, paint)
            }

            //Hour line
            it.save()
            paint.color = hourLineColor
            paint.strokeWidth = hourLineWidth
            it.rotate(DEGREES_360 - (hours * DEGREES_30) - (minutes * DEGREES_0_5))
            it.drawLine(
                START_X_VALUE,
                START_Y_VALUE * hourLineLength,
                STOP_X_VALUE,
                STOP_Y_VALUE * hourLineLength,
                paint
            )
            it.restore()

            //Minute line
            it.save()
            paint.color = minuteLineColor
            paint.strokeWidth = minuteLineWidth
            it.rotate(DEGREES_360 - (minutes * DEGREES_6))
            it.drawLine(
                START_X_VALUE,
                START_Y_VALUE * minuteLineLength,
                STOP_X_VALUE,
                STOP_Y_VALUE * minuteLineLength,
                paint
            )
            it.restore()

            //Second line
            it.save()
            paint.color = secondLineColor
            paint.strokeWidth = secondLineWidth
            it.rotate(DEGREES_360 - (seconds * DEGREES_6))
            it.drawLine(
                START_X_VALUE,
                START_Y_VALUE * secondLineLength,
                STOP_X_VALUE,
                STOP_Y_VALUE * secondLineLength,
                paint
            )
            it.restore()

            //Timer
            if (seconds == 59f) {
                seconds = 0f
                if (minutes == 59f) {
                    minutes = 0f
                    if (hours == 12f) {
                        hours = 0f
                    } else {
                        hours++
                    }
                } else {
                    minutes++
                }
            } else {
                seconds++
            }
            invalidate()
        }
    }

    private fun getCorrectRadius(): Float {
        val centerX = (width / 2).toFloat()
        val centerY = (height / 2).toFloat()

        return if (centerX < centerY) {
            centerX
        } else {
            centerY
        }
    }

    companion object {
        private const val MAX_VALUE_360 = 360
        private const val MAX_VALUE_12 = 12
        private const val STEP_IN_360 = Math.PI * 2 / MAX_VALUE_360
        private const val STEP_IN_12 = Math.PI * 2 / MAX_VALUE_12
        private const val CLOCK_RADIUS_LINE_SCALE = 0.9f
        private const val CLOCK_STROKE_WIDTH = 0.04f
        private const val HOUR_LINE_WIDTH = CLOCK_STROKE_WIDTH * 0.8f
        private const val MINUTE_LINE_WIDTH = CLOCK_STROKE_WIDTH
        private const val SECOND_LINE_WIDTH = CLOCK_STROKE_WIDTH * 0.5f
        private const val HOUR_LINE_DEFAULT_COLOR = Color.RED
        private const val MINUTE_LINE_DEFAULT_COLOR = Color.BLACK
        private const val SECOND_LINE_DEFAULT_COLOR = Color.BLUE
        private const val START_X_VALUE = 0f
        private const val STOP_X_VALUE = 0f
        private const val START_Y_VALUE = -0.3f
        private const val STOP_Y_VALUE = 0.8f
        private const val HOUR_LINE_LENGTH = 0.55f
        private const val MINUTE_LINE_LENGTH = 1.0f
        private const val SECOND_LINE_LENGTH = 0.3f
        private const val START_VALUE = 0f
        private const val DEGREES_360 = 360f
        private const val DEGREES_30 = 30f
        private const val DEGREES_6 = 6f
        private const val DEGREES_0_5 = 0.5f
    }

}
