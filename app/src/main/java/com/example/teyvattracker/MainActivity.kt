package com.example.teyvattracker

import android.app.Activity
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.graphics.Color
import android.util.TypedValue

class MainActivity : Activity() {
    private val captureRequestCode = 4201
    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(22), dp(24), dp(22), dp(24))
            setBackgroundColor(Color.rgb(18, 24, 34))
        }

        val scroll = ScrollView(this)
        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
        }

        val title = TextView(this).apply {
            text = "Teyvat Tracker"
            setTextColor(Color.WHITE)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 28f)
            gravity = Gravity.CENTER
        }
        content.addView(title, matchWrap())

        val subtitle = TextView(this).apply {
            text = "Помощник для отслеживания сундуков, окулусов и других объектов Genshin Impact"
            setTextColor(Color.rgb(205, 215, 230))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            gravity = Gravity.CENTER
            setPadding(0, dp(12), 0, dp(22))
        }
        content.addView(subtitle, matchWrap())

        val info = TextView(this).apply {
            text = "Этап 1 — проверка сборки и разрешения захвата экрана.\\n\\nВажно: эта ранняя версия ещё НЕ распознаёт сундуки автоматически и не отмечает точки в HoYoLAB. Для этого понадобится следующий этап: распознавание изображения и безопасная интеграция с картой."
            setTextColor(Color.WHITE)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
            setPadding(dp(14), dp(14), dp(14), dp(14))
            setBackgroundColor(Color.rgb(34, 44, 60))
        }
        content.addView(info, matchWrap())

        val captureButton = Button(this).apply {
            text = "Проверить разрешение захвата экрана"
            setOnClickListener { requestCapturePermission() }
        }
        val buttonParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply { topMargin = dp(22) }
        content.addView(captureButton, buttonParams)

        status = TextView(this).apply {
            text = "Статус: ожидание"
            setTextColor(Color.rgb(170, 220, 180))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
            setPadding(0, dp(18), 0, 0)
        }
        content.addView(status, matchWrap())

        val privacy = TextView(this).apply {
            text = "Захват экрана запускается только после твоего нажатия и системного подтверждения Android. Не вводи пароль HoYoverse в сторонние приложения."
            setTextColor(Color.rgb(180, 190, 205))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
            setPadding(0, dp(22), 0, 0)
        }
        content.addView(privacy, matchWrap())

        scroll.addView(content)
        root.addView(scroll, LinearLayout.LayoutParams(-1, -1))
        setContentView(root)
    }

    private fun requestCapturePermission() {
        val manager = getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        startActivityForResult(manager.createScreenCaptureIntent(), captureRequestCode)
    }

    @Deprecated("Deprecated in Android API; retained for broad device compatibility")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == captureRequestCode) {
            status.text = if (resultCode == RESULT_OK && data != null) {
                "Статус: разрешение выдано. Захват пока не запускается — это проверка первого этапа."
            } else {
                "Статус: разрешение не выдано."
            }
        }
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()

    private fun matchWrap() = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
}
