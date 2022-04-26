package com.example.backgroundprocess

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            Handler(Looper.getMainLooper()).postDelayed({
                val color: Int =
                    Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
                it.setBackgroundColor(color)
            },2000)
        }

        findViewById<Button>(R.id.button2).setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                val color = getColorWithDelay()
                it.setBackgroundColor(color)
            }

        }

    }

    private suspend fun getColorWithDelay(): Int{
        return GlobalScope.async(Dispatchers.Default) {
            delay(2000)
            return@async Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
        }.await()
    }
}