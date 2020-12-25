package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.FrameLayout
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel
import org.json.JSONObject

private const val CHANNEL = "com.experiment.success/result"

class MainActivity : AppCompatActivity() {
    lateinit var flutterEngine : FlutterEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        flutterEngine = FlutterEngine(this)

        flutterEngine.dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        )

        FlutterEngineCache
                .getInstance()
                .put(FLUTTER_ENGINE_ID, flutterEngine)

        val json = JSONObject()

        json.put("amount", 500)
        json.put("count", 4)
        json.put("percent", 100)

        val methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
        methodChannel.setMethodCallHandler { call, _ ->
            Log.i("Hello",call.method)
            if(call.method == "getCalculatedResult"){
                Log.i("Hello",call.arguments.toString())
            }
        }

        val button = findViewById<Button>(R.id.mybutton)
        button.setOnClickListener{
            Log.i("Hello","Clicked")
            methodChannel.invokeMethod("getCalculatedResult", json.toString())
        }
    }
}