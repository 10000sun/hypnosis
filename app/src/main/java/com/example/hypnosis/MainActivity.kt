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
        // 쨍한 원색 무지개 대각선 그라데이션
        val colors = intArrayOf("#FF0000", "#FF00FF", "#0000FF", "#00FFFF", "#00FF00", "#FFFF00", "#FF8000")
            .map { Color.parseColor(it) }.toIntArray()
        findViewById<View>(R.id.mainRoot).background =
            GradientDrawable(GradientDrawable.Orientation.TL_BR, colors)
        findViewById<Button>(R.id.startButton).setOnClickListener {
            startActivity(Intent(this, HypnosisActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        // 어색하게 계속 흔들리고 커졌다 작아지는 글자들
        wobble(R.id.top, View.ROTATION, -6f, 6f, 600)
        wobble(R.id.title, View.ROTATION, 5f, -5f, 450)
        wobble(R.id.title, View.SCALE_X, 1f, 1.15f, 300)
        wobble(R.id.title, View.SCALE_Y, 1f, 1.15f, 300)
        wobble(R.id.sub, View.ROTATION, -4f, 4f, 700)
        wobble(R.id.startButton, View.SCALE_X, 1f, 1.2f, 400)
        wobble(R.id.startButton, View.SCALE_Y, 1f, 1.2f, 400)
        animators.forEach { it.start() }
    }

    override fun onStop() {
        animators.forEach { it.cancel() }
        animators.clear()
        super.onStop()
    }

    private fun wobble(id: Int, prop: android.util.Property<View, Float>, from: Float, to: Float, ms: Long) {
        animators += ObjectAnimator.ofFloat(findViewById<View>(id), prop, from, to).apply {
            duration = ms
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
        }
    }
}
