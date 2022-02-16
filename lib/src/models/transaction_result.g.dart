// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'transaction_result.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

TransactionResult _$TransactionResultFromJson(Map<String, dynamic> json) {
  return TransactionResult(
    isTransactionCanceled: json['isTransactionCanceled'] as bool,
    transactionStatus: _$enumDecodeNullable(
        _$TransactionResultStatusEnumMap, json['transactionStatus']),
    statusMessage: json['statusMessage'] as String?,
    transactionId: json['transactionId'] as String?,
    orderId: json['orderId'] as String?,
    paymentType: json['paymentType'] as String?,
  );
}

Map<String, dynamic> _$TransactionResultToJson(TransactionResult instance) =>
    <String, dynamic>{
      'isTransactionCanceled': instance.isTransactionCanceled,
      'transactionStatus':
          _$TransactionResultStatusEnumMap[instance.transactionStatus],
      'statusMessage': instance.statusMessage,
      'transactionId': instance.transactionId,
      'orderId': instance.orderId,
      'paymentType': instance.paymentType,
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
  TransactionResultStatus.capture: 'capture',
  TransactionResultStatus.settlement: 'settlement',
  TransactionResultStatus.pending: 'pending',
  TransactionResultStatus.deny: 'deny',
  TransactionResultStatus.expire: 'expire',
  TransactionResultStatus.cancel: 'cancel',
};
