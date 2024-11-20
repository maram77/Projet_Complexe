import 'dart:convert';
import 'package:flutter/material.dart';
import '../modal/OrderDetail.dart';
import '../service/api_service.dart';
import 'package:http/http.dart' as http;

class Deliveries extends StatefulWidget {
  @override
  _DeliveriesState createState() => _DeliveriesState();
}

class _DeliveriesState extends State<Deliveries> {
  late Future<List<OrderDetail>> ordersFuture;

  @override
  void initState() {
    super.initState();
    ordersFuture = ApiService().fetchOrders();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        elevation: 0,
        automaticallyImplyLeading: false,
        title: Text('Deliveries'),
      ),
      body: FutureBuilder<List<OrderDetail>>(
        future: ordersFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          } else if (snapshot.data == null || snapshot.data!.isEmpty) {
            return Center(child: Text("No orders found"));
          }

          return ListView.builder(
            itemCount: snapshot.data!.length,
            itemBuilder: (context, index) {
              OrderDetail order = snapshot.data![index];
              bool isDelivered = order.orderStatus == 'Delivered';
              return Card(
                margin: EdgeInsets.all(10),
                child: ListTile(
                  leading: Icon(Icons.shopping_cart, color: Color(0xFFF4CC1D)),
                  title: Text('Order ID: ${order.orderId ?? "Unknown"}',
                    style: TextStyle(fontWeight: FontWeight.bold),
                  ),
                  subtitle: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text('Cost: \$${order.orderAmount?.toStringAsFixed(2) ?? "Unknown"}'),
                      Text('Status: ${order.orderStatus ?? "Unknown"}'),
                    ],
                  ),
                  isThreeLine: true,
                  onTap: () => _showOrderDetails(context, order),
                  trailing: ElevatedButton(
                    onPressed: isDelivered ? null : () => _changeOrderStatus(index, snapshot.data!),
                    child: Text('Deliver'),
                    style: ElevatedButton.styleFrom(
                      backgroundColor: isDelivered ? Colors.grey : Colors.green,
                      foregroundColor: Colors.white,
                      disabledBackgroundColor: Colors.grey,
                      disabledForegroundColor: Colors.white.withOpacity(0.38),
                    ),
                  ),
                ),
              );
            },
          );
        },
      ),
    );
  }

  void _showOrderDetails(BuildContext context, OrderDetail order) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text('Order Details'),
        content: SingleChildScrollView(
          child: ListBody(
            children: <Widget>[
              Text('Delivery Address: ${order.delivery?.address ?? "Not provided"}'),
              Text('Contact Number: ${order.delivery?.contactNumber ?? "Not provided"}'),
              Text('Company Name: ${order.delivery?.companyName ?? "Not provided"}'),
              Text('City: ${order.delivery?.city ?? "Not provided"}'),
              Text('State: ${order.delivery?.state ?? "Not provided"}'),
              Text('Postal Code: ${order.delivery?.postCode ?? "Not provided"}'),
            ],
          ),
        ),
        actions: <Widget>[
          TextButton(
            child: Text('Close'),
            onPressed: () => Navigator.of(context). pop(),
          ),
        ],
      ),
    );
  }

  void _changeOrderStatus(int index, List<OrderDetail> orders) async {
    final orderDetail = orders[index];
    orderDetail.orderStatus = 'Delivered';

    final url = Uri.parse('http://localhost:8090/api/auth/checkout/sendOrderStatus');

    final requestBody = json.encode({
      'order': {
        'orderId': orderDetail.orderId,
        'orderStatus': orderDetail.orderStatus,
        'orderAmount': orderDetail.orderAmount,
      },
      'status': orderDetail.orderStatus,
    });

    try {
      // Send the POST request
      final response = await http.post(
        url,
        headers: {'Content-Type': 'application/json'},
        body: requestBody,
      );

      if (response.statusCode == 200) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text("Order status sent!"), backgroundColor: Colors.green),
        );
      } else {
        throw Exception('Failed to send order status: ${response.body}');
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Failed to send order status: $e'), backgroundColor: Colors.red),
      );
    }

    setState(() {
      orders[index] = orderDetail;
    });
  }


}
