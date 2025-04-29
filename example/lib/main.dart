import 'package:flutter/material.dart';

import 'package:midtrans_sdk/midtrans_sdk.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await dotenv.load();
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  MidtransSDK? _midtrans;

  @override
  void initState() {
    super.initState();
    initSDK();
  }

  void initSDK() async {
    _midtrans = await MidtransSDK.init(
      config: MidtransConfig(
        clientKey: dotenv.env['MIDTRANS_CLIENT_KEY'] ?? "",
        merchantBaseUrl: dotenv.env['MIDTRANS_MERCHANT_BASE_URL'] ?? "",
        enableLog: true,
        colorTheme: ColorTheme(
          colorPrimary: Theme.of(context).colorScheme.primary,
          colorPrimaryDark: Theme.of(context).colorScheme.primary,
          colorSecondary: Theme.of(context).colorScheme.secondary,
        ),
      ),
    );
    _midtrans!.setTransactionFinishedCallback((result) {
      print(result.transactionId);
      print(result.status);
      print(result.message);
      print(result.paymentType);
    });
  }

  @override
  void dispose() {
    _midtrans?.removeTransactionFinishedCallback();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: const Text('Plugin example app')),
        body: Center(
          child: ElevatedButton(
            child: Text("Pay Now"),
            onPressed: () async {
              _midtrans?.startPaymentUiFlow(
                token: dotenv.env['MIDTRANS_SNAP_TOKEN'],
              );
            },
          ),
        ),
      ),
    );
  }
}
