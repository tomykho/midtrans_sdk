// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'color_theme.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ColorTheme _$ColorThemeFromJson(Map<String, dynamic> json) {
  return ColorTheme(
    colorPrimary: const ColorConverter().fromJson(json['colorPrimary'] as int?),
    colorPrimaryDark:
        const ColorConverter().fromJson(json['colorPrimaryDark'] as int?),
    colorSecondary:
        const ColorConverter().fromJson(json['colorSecondary'] as int?),
  );
}

Map<String, dynamic> _$ColorThemeToJson(ColorTheme instance) =>
    <String, dynamic>{
      'colorPrimary': const ColorConverter().toJson(instance.colorPrimary),
      'colorPrimaryDark':
          const ColorConverter().toJson(instance.colorPrimaryDark),
      'colorSecondary': const ColorConverter().toJson(instance.colorSecondary),
    };
