// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'midtrans_config.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

MidtransConfig _$MidtransConfigFromJson(Map<String, dynamic> json) {
  return MidtransConfig(
    clientKey: json['clientKey'] as String,
    merchantBaseUrl: json['merchantBaseUrl'] as String,
    language: json['language'] as String,
    colorTheme: json['colorTheme'] == null
        ? null
        : ColorTheme.fromJson(json['colorTheme'] as Map<String, dynamic>),
    enableLog: json['enableLog'] as bool,
  );
}

Map<String, dynamic> _$MidtransConfigToJson(MidtransConfig instance) =>
    <String, dynamic>{
      'clientKey': instance.clientKey,
      'merchantBaseUrl': instance.merchantBaseUrl,
      'language': instance.language,
      'colorTheme': instance.colorTheme?.toJson(),
      'enableLog': instance.enableLog,
    };
