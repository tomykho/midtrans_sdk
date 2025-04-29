package dev.tomykho.midtrans_sdk;

import android.app.Activity;
import android.content.Intent;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import com.midtrans.sdk.corekit.models.TransactionResponse;
import com.midtrans.sdk.uikit.api.model.CustomColorTheme;
import com.midtrans.sdk.uikit.api.model.TransactionResult;
import com.midtrans.sdk.uikit.external.UiKitApi;
import com.midtrans.sdk.uikit.internal.util.UiKitConstants;

import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.android.FlutterFragmentActivity;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * MidtransSdkPlugin
 */
public class MidtransSdkPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;
    private FlutterFragmentActivity activity;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "midtrans_sdk");
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        switch (call.method) {
            case "init":
                init(call, result);
                break;
            case "startPaymentUiFlow":
                startPaymentUiFlow(call, result);
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        activity = (FlutterFragmentActivity) binding.getActivity();
        launcher = activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                actResult -> {
                    if (actResult != null && actResult.getResultCode() == Activity.RESULT_OK) {
                        Intent data = actResult.getData();
                        if (data != null) {
                            TransactionResult result = data.getParcelableExtra(UiKitConstants.KEY_TRANSACTION_RESULT);
                            if (result != null) {
                                HashMap<String, Object> arguments = new HashMap<>();
                                arguments.put("message", result.getMessage());
                                arguments.put("status", result.getStatus());
                                arguments.put("transactionId", result.getTransactionId());
                                arguments.put("paymentType", result.getPaymentType());
                                channel.invokeMethod("onTransactionFinished", arguments);
                            }
                        }
                    }
                });
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        channel.setMethodCallHandler(null);
    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
        activity = (FlutterFragmentActivity) binding.getActivity();
    }

    @Override
    public void onDetachedFromActivity() {
        channel.setMethodCallHandler(null);
    }

    private void init(MethodCall call, Result result) {
        String clientKey = call.argument("clientKey");
        String merchantBaseUrl = call.argument("merchantBaseUrl");
        String language = call.argument("language");
        Boolean enableLog = call.argument("enableLog");
        Map<String, String> colorTheme = call.argument("colorTheme");

        if (clientKey != null && merchantBaseUrl != null) {
            UiKitApi.Builder builder = new UiKitApi.Builder()
                    .withMerchantClientKey(clientKey)
                    .withContext(activity)
                    .withMerchantUrl(merchantBaseUrl)
                    .enableLog(enableLog != null ? enableLog : true);

            if (language != null) {
                LocaleListCompat locales = LocaleListCompat.forLanguageTags(language);
                AppCompatDelegate.setApplicationLocales(locales);
            }

            if (colorTheme != null) {
                String colorPrimaryHex = colorTheme.get("colorPrimaryHex");
                String colorPrimaryDarkHex = colorTheme.get("colorPrimaryDarkHex");
                String colorSecondaryHex = colorTheme.get("colorSecondaryHex");
                builder.withColorTheme(
                        new CustomColorTheme(
                                colorPrimaryHex,
                                colorPrimaryDarkHex,
                                colorSecondaryHex
                        )
                );
            }

            builder.build();
        }

        result.success(null);
    }

    private void startPaymentUiFlow(MethodCall call, Result result) {
        String token = call.argument("token");
        UiKitApi.Companion.getDefaultInstance().startPaymentUiFlow(
                activity,
                launcher,
                token,
                null
        );
        result.success(null);
    }

}
