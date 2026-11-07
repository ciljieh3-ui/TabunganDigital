package com.example

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.core.content.FileProvider
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {

    private lateinit var webView: WebView
    private var filePathCallback: ValueCallback<Array<Uri>>? = null

    companion object {
        private const val FILE_CHOOSER_REQUEST_CODE = 2001
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_CHOOSER_REQUEST_CODE) {
            val result = if (resultCode == RESULT_OK) {
                data?.data?.let { arrayOf(it) }
            } else {
                null
            }
            filePathCallback?.onReceiveValue(result)
            filePathCallback = null
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup WindowCompat for edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        webView = WebView(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(Color.parseColor("#08080B"))
            setLayerType(View.LAYER_TYPE_HARDWARE, null)

            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                allowFileAccess = true
                allowContentAccess = true
                mediaPlaybackRequiresUserGesture = false
                cacheMode = WebSettings.LOAD_DEFAULT
                useWideViewPort = true
                loadWithOverviewMode = true
                setSupportZoom(false)
                displayZoomControls = false
            }

            addJavascriptInterface(AndroidBridge(), "AndroidBridge")

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    val url = request?.url?.toString() ?: return false
                    if (url.startsWith("file:///android_asset/")) {
                        return false
                    }
                    return try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                        true
                    } catch (_: Exception) {
                        false
                    }
                }
            }

            webChromeClient = object : WebChromeClient() {
                override fun onShowFileChooser(
                    webView: WebView?,
                    filePathCallback: ValueCallback<Array<Uri>>?,
                    fileChooserParams: FileChooserParams?
                ): Boolean {
                    this@MainActivity.filePathCallback?.onReceiveValue(null)
                    this@MainActivity.filePathCallback = filePathCallback
                    return try {
                        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                            addCategory(Intent.CATEGORY_OPENABLE)
                            type = "application/json"
                        }
                        @Suppress("DEPRECATION")
                        startActivityForResult(Intent.createChooser(intent, "Pilih file cadangan (.json)"), FILE_CHOOSER_REQUEST_CODE)
                        true
                    } catch (_: Exception) {
                        try {
                            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                                addCategory(Intent.CATEGORY_OPENABLE)
                                type = "*/*"
                            }
                            @Suppress("DEPRECATION")
                            startActivityForResult(Intent.createChooser(intent, "Pilih file cadangan"), FILE_CHOOSER_REQUEST_CODE)
                            true
                        } catch (_: Exception) {
                            filePathCallback?.onReceiveValue(null)
                            this@MainActivity.filePathCallback = null
                            false
                        }
                    }
                }
            }
        }

        setContentView(webView)

        // Setup Hardware Back Button Dispatcher
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                webView.evaluateJavascript("window.handleAndroidBack ? window.handleAndroidBack() : false") { result ->
                    val handled = result == "true"
                    if (!handled) {
                        isEnabled = false
                        onBackPressedDispatcher.onBackPressed()
                        isEnabled = true
                    }
                }
            }
        })

        webView.loadUrl("file:///android_asset/index.html")
    }

    override fun onResume() {
        super.onResume()
        webView.onResume()
    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
    }

    override fun onDestroy() {
        webView.destroy()
        super.onDestroy()
    }

    inner class AndroidBridge {
        @JavascriptInterface
        fun setTheme(isLight: Boolean) {
            runOnUiThread {
                val controller = WindowInsetsControllerCompat(window, window.decorView)
                controller.isAppearanceLightStatusBars = isLight
                controller.isAppearanceLightNavigationBars = isLight
                val bgColor = if (isLight) Color.parseColor("#EAF7F9") else Color.parseColor("#08080B")
                window.decorView.setBackgroundColor(bgColor)
                webView.setBackgroundColor(bgColor)
            }
        }

        @JavascriptInterface
        fun hapticFeedback() {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                    vibratorManager?.defaultVibrator?.vibrate(
                        VibrationEffect.createOneShot(12, VibrationEffect.DEFAULT_AMPLITUDE)
                    )
                } else {
                    @Suppress("DEPRECATION")
                    val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator?.vibrate(VibrationEffect.createOneShot(12, VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        @Suppress("DEPRECATION")
                        vibrator?.vibrate(12)
                    }
                }
            } catch (_: Exception) {}
        }

        @JavascriptInterface
        fun exportData(fileName: String, content: String, mimeType: String) {
            runOnUiThread {
                try {
                    val exportDir = File(cacheDir, "exports").apply { mkdirs() }
                    val file = File(exportDir, fileName)
                    FileOutputStream(file).use { out ->
                        out.write(content.toByteArray(Charsets.UTF_8))
                    }
                    val uri = FileProvider.getUriForFile(
                        this@MainActivity,
                        "${applicationContext.packageName}.fileprovider",
                        file
                    )
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = mimeType.ifEmpty { "text/plain" }
                        putExtra(Intent.EXTRA_STREAM, uri)
                        putExtra(Intent.EXTRA_SUBJECT, fileName)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    val chooser = Intent.createChooser(shareIntent, "Bagikan / Simpan: $fileName")
                    startActivity(chooser)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
