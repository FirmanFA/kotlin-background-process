package com.example.backgroundprocess

import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.net.URL
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var asyncBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        asyncBtn = findViewById(R.id.button3)

        asyncBtn.setOnClickListener {
            AsyncExample().execute()
        }

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

    inner class AsyncExample: AsyncTask<Unit,Int,Int>(){

        override fun doInBackground(vararg p0: Unit?): Int {
            Thread.sleep(2000)
            return 0
        }

        override fun onPostExecute(result: Int?) {
            val color: Int =
                Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            asyncBtn.setBackgroundColor(color)
        }

    }
}