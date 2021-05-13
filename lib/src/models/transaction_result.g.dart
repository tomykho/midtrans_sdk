// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'transaction_result.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

TransactionResult _$TransactionResultFromJson(Map<String, dynamic> json) {
  return TransactionResult(
    isTransactionCanceled: json['isTransactionCanceled'] as bool,
    source: json['source'] as String?,
    status:
        _$enumDecodeNullable(_$TransactionResultStatusEnumMap, json['status']),
    statusMessage: json['statusMessage'] as String?,
  );
}

Map<String, dynamic> _$TransactionResultToJson(TransactionResult instance) =>
    <String, dynamic>{
      'isTransactionCanceled': instance.isTransactionCanceled,
      'source': instance.source,
      'status': _$TransactionResultStatusEnumMap[instance.status],
      'statusMessage': instance.statusMessage,
    };

K _$enumDecode<K, V>(
  Map<K, V> enumValues,
  Object? source, {
  K? unknownValue,
}) {
  if (source == null) {
    throw ArgumentError(
      'A value must be provided. Supported values: '
      '${enumValues.values.join(', ')}',
    );
  }

  return enumValues.entries.singleWhere(
    (e) => e.value == source,
    orElse: () {
      if (unknownValue == null) {
        throw ArgumentError(
          '`$source` is not one of the supported values: '
          '${enumValues.values.join(', ')}',
        );
      }
      return MapEntry(unknownValue, enumValues.values.first);
    },
  ).key;
}

K? _$enumDecodeNullable<K, V>(
  Map<K, V> enumValues,
  dynamic source, {
  K? unknownValue,
}) {
  if (source == null) {
    return null;
  }
  return _$enumDecode<K, V>(enumValues, source, unknownValue: unknownValue);
}

const _$TransactionResultStatusEnumMap = {
  TransactionResultStatus.success: 'success',
  TransactionResultStatus.pending: 'pending',
  TransactionResultStatus.invalid: 'invalid',
  TransactionResultStatus.failed: 'failed',
};
