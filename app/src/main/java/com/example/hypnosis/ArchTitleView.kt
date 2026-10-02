package com.example.hypnosis

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos

/** 글자마다 색이 다르고 테두리·그림자가 있는, 아치 모양으로 휜 제목 */
class ArchTitleView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val text = context.getString(R.string.title)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        typeface = Typeface.DEFAULT_BOLD
        strokeJoin = Paint.Join.ROUND
    }
    private val path = Path()
    private var radius = 0f

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        val w = MeasureSpec.getSize(widthSpec)
        paint.textSize = w * 0.17f
        radius = w * 0.8f
        val half = paint.measureText(text) / radius / 2f
        val drop = radius * (1f - cos(half))
        setMeasuredDimension(w, (paint.textSize * 1.6f + drop).toInt())
    }

    override fun onDraw(canvas: Canvas) {
        val w = width.toFloat()
        val cx = w / 2f
        val cy = paint.textSize * 1.2f + radius
        val half = Math.toDegrees((paint.measureText(text) / radius / 2f).toDouble()).toFloat()
        path.reset()
        path.addArc(RectF(cx - radius, cy - radius, cx + radius, cy + radius), -90f - half, half * 2f)

        // 그림자(주황) → 테두리(진한 회색) → 글자색(무지개) 순서로 그림
        for (pass in 0..2) {
            canvas.save()
            if (pass == 0) canvas.translate(paint.textSize * 0.07f, paint.textSize * 0.07f)
            text.forEachIndexed { i, ch ->
                val offset = paint.measureText(text.substring(0, i))
                when (pass) {
                    0 -> { paint.style = Paint.Style.FILL_AND_STROKE; paint.strokeWidth = paint.textSize * 0.1f; paint.color = Color.parseColor("#FF9800") }
                    1 -> { paint.style = Paint.Style.STROKE; paint.strokeWidth = paint.textSize * 0.1f; paint.color = Color.parseColor("#333333") }
                    else -> { paint.style = Paint.Style.FILL; paint.color = Color.HSVToColor(floatArrayOf(i * 300f / text.length.coerceAtLeast(1), 0.7f, 1f)) }
                }
                canvas.drawTextOnPath(ch.toString(), path, offset, 0f, paint)
            }
            canvas.restore()
        }
    }
}
