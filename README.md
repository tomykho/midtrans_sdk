# midtrans_sdk

A Flutter plugin for [Midtrans Mobile SDK](https://mobile-docs.midtrans.com/). Visit https://midtrans.com.

[![pub package](https://img.shields.io/pub/v/midtrans_sdk.svg)](https://pub.dartlang.org/packages/midtrans_sdk)

---

### Table of content

- [Getting started](#getting-started)

---

### Supported Platforms

- Android
- iOS

## <a id="getting-started"> **Getting started**

See the [example](example) directory for a sample about start payment by using snap token app which using `midtrans_sdk`.

### <a id="midtrans-config"> MidtransConfig

To start using Midtrans you first need to create an instance of `MidtransSDK` before using any other of our sdk functionalities.  

`MidtransSDK` receives a `MitransConfig` object. This is how you can configure our `MidtransSDK` instance and connect it to your Midtrans account.

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

### <a id="init-sdk">Initializing the SDK

The next step is to call `init` which have the required `MitransConfig` object parameter `config`.

After we call `init` we can use all of Midtrans SDK features.

Initialize the SDK to enable Midtrans to start payment.

```dart
MidtransSDK.init(
    config: config,
);
```

---
