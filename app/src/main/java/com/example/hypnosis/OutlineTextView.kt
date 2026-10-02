package com.example.hypnosis

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView

/** 글자색(textColor) 바깥에 두꺼운 테두리를 두르는 TextView */
class OutlineTextView(context: Context, attrs: AttributeSet? = null) : TextView(context, attrs) {
    private var strokeColor = 0
    private var strokeWidthPx = 0f
    private var drawing = false

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.OutlineTextView)
        strokeColor = a.getColor(R.styleable.OutlineTextView_strokeColor, 0)
        strokeWidthPx = a.getDimension(R.styleable.OutlineTextView_strokeWidth, 0f)
        a.recycle()
    }

    override fun onDraw(canvas: android.graphics.Canvas) {
        if (strokeWidthPx <= 0f || drawing) return super.onDraw(canvas)
        drawing = true
        val fill = currentTextColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidthPx
        paint.strokeJoin = Paint.Join.ROUND
        setTextColor(strokeColor)
        super.onDraw(canvas)
        paint.style = Paint.Style.FILL
        setTextColor(fill)
        super.onDraw(canvas)
        drawing = false
    }
}
