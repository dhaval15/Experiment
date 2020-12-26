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

        // Mandatory setups

        flutterEngine = FlutterEngine(this)

        flutterEngine.dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        )

        FlutterEngineCache
                .getInstance()
                .put(FLUTTER_ENGINE_ID, flutterEngine)

        val methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)

        //

        val json = JSONObject()

        json.put("x", 20)
        json.put("y", 5)

        methodChannel.setMethodCallHandler { call, _ ->

            // All the results will be here the developer needs to check from which method is coming from.
            Log.i("Flutter_SDK",call.method)
            if(call.method == "add"){
                Log.i("Flutter_SDK",call.arguments.toString())
            }
        }

        val button = findViewById<Button>(R.id.mybutton)
        button.setOnClickListener{
            Log.i("Flutter_SDK","Clicked")
            // Developer can call methods from sdk anywhere.
            // But he will not get the result at that call instantly
            // and neither he can chain the listener to wait for result there.
            methodChannel.invokeMethod("add", json.toString())
        }
    }
}