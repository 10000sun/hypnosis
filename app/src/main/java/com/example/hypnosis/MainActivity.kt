package com.example.hypnosis

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.TextView

class MainActivity : Activity() {
    private val animators = mutableListOf<ValueAnimator>()
    private val bg = GradientDrawable(GradientDrawable.Orientation.TL_BR, IntArray(7))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.mainRoot).background = bg
        findViewById<Button>(R.id.startButton).setOnClickListener {
            startActivity(Intent(this, HypnosisActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        val title = findViewById<TextView>(R.id.title)
        val button = findViewById<View>(R.id.startButton)
        val text = title.text.toString()

        // 배경과 제목 글자 색이 무지개로 계속 변함
        animators += ValueAnimator.ofFloat(0f, 360f).apply {
            duration = 3000
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                val offset = it.animatedValue as Float
                bg.colors = IntArray(7) { i -> Color.HSVToColor(floatArrayOf((offset + i * 51f) % 360f, 0.85f, 1f)) }
                val span = SpannableString(text)
                text.forEachIndexed { i, _ ->
                    val c = Color.HSVToColor(floatArrayOf((offset * 2 + 180f + i * 60f) % 360f, 1f, 1f))
                    span.setSpan(ForegroundColorSpan(c), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                title.text = span
            }
        }
        // 제목은 흔들리고, 버튼은 두근두근
        animators += ObjectAnimator.ofFloat(title, View.ROTATION, -8f, 8f).apply { wobble(500) }
        animators += ObjectAnimator.ofFloat(title, View.SCALE_X, 1f, 1.12f).apply { wobble(350) }
        animators += ObjectAnimator.ofFloat(title, View.SCALE_Y, 1f, 1.12f).apply { wobble(350) }
        animators += ObjectAnimator.ofFloat(button, View.SCALE_X, 1f, 1.2f).apply { wobble(400) }
        animators += ObjectAnimator.ofFloat(button, View.SCALE_Y, 1f, 1.2f).apply { wobble(400) }
        animators += ObjectAnimator.ofFloat(button, View.ROTATION, 4f, -4f).apply { wobble(700) }
        animators.forEach { it.start() }
    }

    override fun onStop() {
        animators.forEach { it.cancel() }
        animators.clear()
        super.onStop()
    }

    private fun ValueAnimator.wobble(ms: Long) {
        duration = ms
        repeatCount = ValueAnimator.INFINITE
        repeatMode = ValueAnimator.REVERSE
    }
}
