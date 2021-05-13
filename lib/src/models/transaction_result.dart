import 'package:json_annotation/json_annotation.dart';

part 'transaction_result.g.dart';

@JsonSerializable()
class TransactionResult {
  final bool isTransactionCanceled;
  final String? source;
  final TransactionResultStatus? status;
  final String? statusMessage;

  TransactionResult({
    this.isTransactionCanceled = true,
    this.source,
    this.status,
    this.statusMessage,
  });

  factory TransactionResult.fromJson(Map<String, dynamic> json) =>
      _$TransactionResultFromJson(json);
  Map<String, dynamic> toJson() => _$TransactionResultToJson(this);
}

enum TransactionResultStatus {
  success,
  pending,
  invalid,
  failed,
}
