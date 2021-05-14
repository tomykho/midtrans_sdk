# midtrans_sdk

A Flutter plugin for [Midtrans Mobile SDK](https://mobile-docs.midtrans.com/). Visit https://midtrans.com.

[![pub package](https://img.shields.io/pub/v/midtrans_sdk.svg)](https://pub.dartlang.org/packages/midtrans_sdk)

---

## Supported Platforms

- Android
- iOS

## Usage

To use this plugin, add `midtrans_sdk` as a dependency in your [pubspec.yaml](https://flutter.dev/docs/development/packages-and-plugins/using-packages) file.

## Getting started

See the [example](example) directory for a sample about start payment by using snap token app which using `midtrans_sdk`.

### Android

Midtrans SDK UIKit requires [Appcompat](https://developer.android.com/jetpack/androidx/releases/appcompat) to open payment UI flow screen.

Open `styles.xml` file and add `AppTheme` style to the file.

```xml
<style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
```

Open the `AndroidManifest.xml` file and use `AppTheme` style for your application theme.

```xml
<manifest>
  ...
  <application
      android:label="midtrans_sdk_example"
      tools:replace="android:label"
      android:theme="@style/AppTheme"
     >
     ...
  </application>
  ...
</manifest>
```

### MidtransConfig

To start using Midtrans you first need to create an instance of `MidtransSDK` before using any other of our sdk functionalities.  
`MidtransSDK` receives a `MidtransConfig` object. This is how you can configure our `MidtransSDK` instance and connect it to your Midtrans account.

*Example:*
```dart
import 'package:midtrans_sdk/midtrans_sdk.dart';

var config = MidtransConfig(
  clientKey: DotEnv.env['MIDTRANS_CLIENT_KEY'] ?? "",
  merchantBaseUrl: DotEnv.env['MIDTRANS_MERCHANT_BASE_URL'] ?? "",
  colorTheme: ColorTheme(
    colorPrimary: Theme.of(context).accentColor,
    colorPrimaryDark: Theme.of(context).accentColor,
    colorSecondary: Theme.of(context).accentColor,
  ),
);
```

---

### Initializing the SDK

The next step is to call `init` which have the required `MitransConfig` object parameter `config`.
After we call `init` we can use all of Midtrans SDK features.
Initialize the SDK to enable Midtrans to start payment.

```dart
MidtransSDK.init(
    config: config,
);
```

---

## Starting payment
  
### Start payment method screen
  
Default mode for `midtrans_sdk` is showing payment method screen. This screen will show all of your available payment methods.
You can enable/disable payment methods via Snap Preferences in [MAP](https://account.midtrans.com).

### Start payment by using snap token
  
We provide SDK method to allow you to make payment by using snap token without initialize transaction request first. You just need to pass snap token as argument of `startPaymentUiFlow` method

