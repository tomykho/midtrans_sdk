package dev.tomykho.midtrans_sdk;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.Logger;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.UIKitCustomSetting;
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme;
import com.midtrans.sdk.corekit.models.TransactionResponse;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** MidtransSdkPlugin */
public class MidtransSdkPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private Activity activity;

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
      case "setUIKitCustomSetting":
        setUIKitCustomSetting(call, result);
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
    activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
    channel.setMethodCallHandler(null);
  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
    activity = binding.getActivity();
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
    Map<String, Long> colorTheme = call.argument("colorTheme");
    SdkUIFlowBuilder builder = SdkUIFlowBuilder.init()
            .setContext(activity)
            .setClientKey(clientKey)
            .setMerchantBaseUrl(merchantBaseUrl)
            .setLanguage(language)
            .enableLog(enableLog != null ? enableLog : true)
            .setTransactionFinishedCallback(new TransactionFinishedCallback() {
              @Override
              public void onTransactionFinished(TransactionResult transactionResult) {
                HashMap<String, Object> arguments = new HashMap<>();
                arguments.put("status", transactionResult.getStatus());
                arguments.put("source", transactionResult.getSource());
                arguments.put("statusMessage", transactionResult.getStatusMessage());
                arguments.put("isTransactionCanceled", transactionResult.isTransactionCanceled());
                channel.invokeMethod("onTransactionFinished", arguments);
              }
            });
    if (colorTheme != null) {
      Long colorPrimary = colorTheme.get("colorPrimary");
      Long colorPrimaryDark = colorTheme.get("colorPrimaryDark");
      Long colorSecondary = colorTheme.get("colorSecondary");
      builder.setColorTheme(
              new CustomColorTheme(
                      Long.toString(colorPrimary != null ? colorPrimary : 0, 16),
                      Long.toString(colorPrimaryDark != null ? colorPrimaryDark : 0, 16),
                      Long.toString(colorSecondary != null ? colorSecondary : 0, 16)
              )
      );
    }
    builder.buildSDK();
    result.success(null);
  }

  private void setUIKitCustomSetting(MethodCall call, Result result) {
    Boolean showPaymentStatus = call.argument("showPaymentStatus");
    Boolean saveCardChecked = call.argument("saveCardChecked");
    Boolean skipCustomerDetailsPages = call.argument("skipCustomerDetailsPages");
    Boolean showEmailInCcForm = call.argument("showEmailInCcForm");
    Boolean setEnableAutoReadSms = call.argument("setEnableAutoReadSms");

    UIKitCustomSetting setting = new UIKitCustomSetting();
    if (showPaymentStatus != null) {
      setting.setShowPaymentStatus(showPaymentStatus);
    }
    if (saveCardChecked != null) {
      setting.setShowPaymentStatus(saveCardChecked);
    }
    if (skipCustomerDetailsPages != null) {
      setting.setSkipCustomerDetailsPages(skipCustomerDetailsPages);
    }
    if (showEmailInCcForm != null) {
      setting.setShowEmailInCcForm(showEmailInCcForm);
    }
    if (showEmailInCcForm != null) {
      setting.setShowEmailInCcForm(showEmailInCcForm);
    }
    if (setEnableAutoReadSms != null) {
      setting.setEnableAutoReadSms(setEnableAutoReadSms);
    }
    MidtransSDK.getInstance().setUIKitCustomSetting(setting);
    result.success(null);
  }

  private void startPaymentUiFlow(MethodCall call, Result result) {
    String token = call.argument("token");
    MidtransSDK.getInstance().startPaymentUiFlow(activity, token);
    result.success(null);
  }

}
