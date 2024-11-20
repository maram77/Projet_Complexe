import 'package:http/http.dart' as http;
import 'dart:convert';

class Api_stateService {
  final String baseUrl = 'http://localhost:8090/api/auth/checkout/';

  Future<bool> updateOrderStatus(int orderId, String newStatus) async {
    final url = Uri.parse('$baseUrl/api/orders/$orderId/status');
    final response = await http.put(
      url,
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'status': newStatus}),
    );

    return response.statusCode == 200;
  }
}
