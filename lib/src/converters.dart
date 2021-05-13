import 'dart:ui';
import 'package:json_annotation/json_annotation.dart';

class ColorConverter implements JsonConverter<Color?, int?> {
  const ColorConverter();

  @override
  Color? fromJson(int? value) {
    return Color(value ?? 0);
  }

  @override
  int? toJson(Color? color) => color?.value;
}
