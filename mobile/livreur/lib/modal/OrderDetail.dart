import 'User.dart';   // Adjust import path as needed
import 'Delivery.dart';  // Adjust import path as needed

class OrderDetail {
  final int? orderId;
   String? orderStatus;
  final double? orderAmount;
  final User? user;
  final Delivery? delivery;

  OrderDetail({this.orderId, this.orderStatus, this.orderAmount, this.user, this.delivery});

  factory OrderDetail.fromJson(Map<String, dynamic> json) {
    return OrderDetail(
      orderId: json['orderId'] as int?,
      orderStatus: json['orderStatus'] as String?,
      orderAmount: (json['orderAmount'] as num?)?.toDouble(),
      user: json['user'] != null ? User.fromJson(json['user']) : null,
      delivery: json['delivery'] != null ? Delivery.fromJson(json['delivery']) : null,
    );
  }
}

