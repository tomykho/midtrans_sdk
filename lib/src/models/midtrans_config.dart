import 'package:midtrans_sdk/src/models/color_theme.dart';

class MidtransConfig {
  final String clientKey;
  final String merchantBaseUrl;
  final String? language;
  final bool enableLog;
  final ColorTheme? colorTheme;

  MidtransConfig({
    required this.clientKey,
    required this.merchantBaseUrl,
    this.language = 'id',
    this.enableLog = false,
    this.colorTheme,
  });

  Map<String, dynamic> toJson() {
    return {
      'clientKey': clientKey,
      'merchantBaseUrl': merchantBaseUrl,
      'language': language,
      'enableLog': enableLog,
      'colorTheme': colorTheme?.toJson(),
    };
  }
}
