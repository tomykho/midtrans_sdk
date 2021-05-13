import 'package:json_annotation/json_annotation.dart';
import 'package:midtrans_sdk/src/models/color_theme.dart';

part 'midtrans_config.g.dart';

@JsonSerializable(explicitToJson: true)
class MidtransConfig {
  final String clientKey;
  final String merchantBaseUrl;
  final String language;
  final ColorTheme? colorTheme;
  final bool enableLog;

  MidtransConfig({
    required this.clientKey,
    required this.merchantBaseUrl,
    this.language = 'id',
    this.colorTheme,
    this.enableLog = true,
  });

  factory MidtransConfig.fromJson(Map<String, dynamic> json) =>
      _$MidtransConfigFromJson(json);
  Map<String, dynamic> toJson() => _$MidtransConfigToJson(this);
}
