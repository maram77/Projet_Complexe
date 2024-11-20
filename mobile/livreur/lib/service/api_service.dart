import 'dart:convert';
import 'package:http/http.dart' as http;
import '../modal/OrderDetail.dart';

class ApiService {
  static const String baseUrl = 'http://localhost:8090/api/auth/checkout/orders';

  Future<List<OrderDetail>> fetchOrders() async {
    final response = await http.get(Uri.parse(baseUrl));
    if (response.statusCode == 200) {
      print("Raw JSON response:");
      print(response.body);  // Prints raw JSON response to the console

      List<dynamic> orderJson = json.decode(response.body);
      List<OrderDetail> orders = orderJson.map((json) => OrderDetail.fromJson(json)).toList();

      print("Parsed Order Details:");
      orders.forEach((order) {
        print("Order ID: ${order.orderId}, Status: ${order.orderStatus}, Amount: ${order.orderAmount}");
        // Additional details can be printed as needed
      });

      return orders;
    } else {
      throw Exception('Failed to load orders, Status code: ${response.statusCode}');
    }
  }
}