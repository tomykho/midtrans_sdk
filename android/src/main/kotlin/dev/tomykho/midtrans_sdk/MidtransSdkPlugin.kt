package dev.tomykho.midtrans_sdk

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.midtrans.sdk.uikit.api.model.CustomColorTheme
import com.midtrans.sdk.uikit.api.model.TransactionResult
import com.midtrans.sdk.uikit.external.UiKitApi
import io.flutter.embedding.android.FlutterFragmentActivity
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

/**
 * MidtransSdkPlugin
 */
class MidtransSdkPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    /** The MethodChannel that will the communication between Flutter and native Android
     *
     * This local reference serves to register the plugin with the Flutter Engine and unregister it
     * when the Flutter Engine is detached from the Activity */
    private lateinit var channel: MethodChannel
    private lateinit var activity: FlutterFragmentActivity
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onAttachedToEngine(binding: FlutterPluginBinding) {
        channel = MethodChannel(binding.binaryMessenger, "midtrans_sdk")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "init" -> init(call, result)
            "startPaymentUiFlow" -> startPaymentUiFlow(call, result)
            else -> result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity as FlutterFragmentActivity
        launcher = activity.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { actResult ->
            if (actResult?.resultCode == Activity.RESULT_OK) {
                val data = actResult.data ?: return@registerForActivityResult
                val key = "UiKitConstants.key_transaction_result";
                val txn: TransactionResult? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    data.getParcelableExtra(key, TransactionResult::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    data.getParcelableExtra(key)
                }

                txn?.let {
                    val args = hashMapOf<String, Any?>(
                        "message" to it.message,
                        "status" to it.status,
                        "transactionId" to it.transactionId,
                        "paymentType" to it.paymentType
                    )
                    channel.invokeMethod("onTransactionFinished", args)
                }
            }
        }
    }

    override fun onDetachedFromActivityForConfigChanges() {
        channel.setMethodCallHandler(null)
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        activity = binding.activity as FlutterFragmentActivity
    }

    override fun onDetachedFromActivity() {
        channel.setMethodCallHandler(null)
    }

    private fun init(call: MethodCall, result: MethodChannel.Result) {
        val clientKey: String? = call.argument("clientKey")
        val merchantBaseUrl: String? = call.argument("merchantBaseUrl")
        val language: String? = call.argument("language")
        val enableLog: Boolean? = call.argument("enableLog")
        val colorTheme: Map<String, String>? = call.argument("colorTheme")

        if (clientKey != null && merchantBaseUrl != null) {
            val builder = UiKitApi.Builder()
                .withMerchantClientKey(clientKey)
                .withContext(activity)
                .withMerchantUrl(merchantBaseUrl)
                .enableLog(enableLog ?: true)

            if (!language.isNullOrBlank()) {
                val locales = LocaleListCompat.forLanguageTags(language)
                AppCompatDelegate.setApplicationLocales(locales)
            }

            colorTheme?.let { ct ->
                val colorPrimaryHex = ct["colorPrimaryHex"]
                val colorPrimaryDarkHex = ct["colorPrimaryDarkHex"]
                val colorSecondaryHex = ct["colorSecondaryHex"]
                builder.withColorTheme(
                    CustomColorTheme(
                        colorPrimaryHex,
                        colorPrimaryDarkHex,
                        colorSecondaryHex
                    )
                )
            }

            builder.build()
        }

        result.success(null)
    }

    private fun startPaymentUiFlow(call: MethodCall, result: MethodChannel.Result) {
        val token: String? = call.argument("token")
        UiKitApi.getDefaultInstance().startPaymentUiFlow(
            activity,
            launcher,
            token,
            null
        )
    }
}
