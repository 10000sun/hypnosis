package com.example.hypnosis

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : Activity() {
    private val animators = mutableListOf<ValueAnimator>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 파스텔 무지개 대각선 그라데이션
        val colors = intArrayOf("#FF8A80", "#FFC85A", "#FFFF70", "#A0FF9A", "#6FD8FF", "#7B7BFF", "#E879C8")
            .map { Color.parseColor(it) }.toIntArray()
        findViewById<View>(R.id.mainRoot).background =
            GradientDrawable(GradientDrawable.Orientation.TL_BR, colors)
        findViewById<Button>(R.id.startButton).setOnClickListener {
            startActivity(Intent(this, HypnosisActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        val title = findViewById<View>(R.id.title)
        val button = findViewById<View>(R.id.startButton)
        animators += ObjectAnimator.ofFloat(title, View.ROTATION, -3f, 3f).apply { wobble(900) }
        animators += ObjectAnimator.ofFloat(button, View.SCALE_X, 1f, 1.12f).apply { wobble(500) }
        animators += ObjectAnimator.ofFloat(button, View.SCALE_Y, 1f, 1.12f).apply { wobble(500) }
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
