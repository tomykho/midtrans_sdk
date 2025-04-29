class TransactionResult {
  final String status;
  final String? transactionId;
  final String? paymentType;
  final String? message;

  TransactionResult({
    required this.status,
    this.transactionId,
    this.paymentType,
    this.message,
  });

  factory TransactionResult.fromJson(Map<String, dynamic> json) {
    return TransactionResult(
      status: json['status'] as String,
      transactionId: json['transactionId'] as String?,
      paymentType: json['paymentType'] as String?,
      message: json['message'] as String?,
    );
  }
}
