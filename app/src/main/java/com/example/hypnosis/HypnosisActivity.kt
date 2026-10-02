package com.example.hypnosis

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.WindowManager

class HypnosisActivity : Activity() {
    private val animators = mutableListOf<ValueAnimator>()

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        setContentView(R.layout.activity_hypnosis)

        val spiral = findViewById<View>(R.id.spiral)
        // 계속 회전 + 커졌다 작아졌다 하는 울렁임
        animators += ObjectAnimator.ofFloat(spiral, View.ROTATION, 0f, 360f).apply {
            duration = 6000
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
        }
        animators += ObjectAnimator.ofFloat(spiral, View.SCALE_X, 1.0f, 1.25f).apply { pulse() }
        animators += ObjectAnimator.ofFloat(spiral, View.SCALE_Y, 1.0f, 1.25f).apply { pulse() }
        animators.forEach { it.start() }
    }

    // 화면 아무 곳이나 터치하면 메인 화면으로
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.actionMasked == MotionEvent.ACTION_UP) {
            finish()
            return true
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun ValueAnimator.pulse() {
        duration = 1800
        repeatCount = ValueAnimator.INFINITE
        repeatMode = ValueAnimator.REVERSE
    }

    override fun onDestroy() {
        animators.forEach { it.cancel() }
        super.onDestroy()
    }
}
