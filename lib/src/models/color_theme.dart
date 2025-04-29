import 'dart:ui';

class ColorTheme {
  Color? colorPrimary;
  Color? colorPrimaryDark;
  Color? colorSecondary;

  ColorTheme({this.colorPrimary, this.colorPrimaryDark, this.colorSecondary});

  Map<String, dynamic> toJson() {
    return {
      'colorPrimaryHex': colorPrimary?.toHex(),
      'colorPrimaryDarkHex': colorPrimaryDark?.toHex(),
      'colorSecondaryHex': colorSecondary?.toHex(),
    };
  }
}

extension HexColor on Color {
  String toHex({bool leadingHashSign = true}) =>
      '#${toARGB32().toRadixString(16).padLeft(8, '0')}';
}
