import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

import '../components/background.dart';

// Function to store JWT token securely
Future<void> storeToken(String token) async {
  SharedPreferences prefs = await SharedPreferences.getInstance();
  prefs.setString('jwt_token', token);
}

class LoginPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  bool _isLoading = false;

  Future<void> storeUserDetails(Map<String, dynamic> userDetails) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.setString('user_firstname', userDetails['firstname']);
    await prefs.setString('user_lastname', userDetails['lastname']);
    await prefs.setString('user_email', userDetails['email']);
    print(userDetails);
    await prefs.setString('user_phone', userDetails['telephone']);
  }

  void _submitForm() async {
    String email = _emailController.text.trim();
    String password = _passwordController.text.trim();

    setState(() {
      _isLoading = true;
    });

    var url = Uri.parse('http://localhost:8090/api/auth/authenticate');
    http.Response? response;

    try {
      response = await http.post(
        url,
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({'email': email, 'password': password}),
      );

      if (response.statusCode == 200) {
        String? jwtToken = response.headers['authorization'];
        if (jwtToken != null && jwtToken.startsWith('Bearer ')) {
          jwtToken = jwtToken.substring(7);
          await storeToken(jwtToken);

          // Decode response body to get user details
          var responseData = jsonDecode(response.body);
          await storeUserDetails(responseData);

          Navigator.pushReplacementNamed(context, '/home');
        } else {
          throw Exception('JWT token not found or invalid');
        }
      } else {
        throw Exception('Failed to authenticate');
      }
    } catch (e) {
      final errorMessage = response != null
          ? 'An error occurred: ${response.statusCode} - ${response.body}'
          : 'An error occurred and no server response was received';

      showDialog(
        context: context,
        builder: (context) =>
            AlertDialog(
              title: Text('Error'),
              content: Text(errorMessage),
              actions: <Widget>[
                TextButton(
                  onPressed: () => Navigator.pop(context),
                  child: Text('OK'),
                ),
              ],
            ),
      );
    } finally {
      setState(() {
        _isLoading = false;
      });
    }
  }
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;

    return Scaffold(
      body: Background(
        child: SingleChildScrollView(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              SizedBox(height: size.height * 0.03),

              Container(
                alignment: Alignment.center,
                margin: EdgeInsets.symmetric(horizontal: 40),
                child: TextField(
                  controller: _emailController,
                  decoration: const InputDecoration(
                    labelText: "Email",
                    labelStyle: TextStyle(color: Color(0xFF4c9c1c)),
                    enabledBorder: UnderlineInputBorder(
                      borderSide: BorderSide(color: Color(0xFF4c9c1c)),
                    ),
                    focusedBorder: UnderlineInputBorder(
                      borderSide: BorderSide(color: Color(0xFF4c9c1c)),
                    ),
                  ),
                ),
              ),

              SizedBox(height: size.height * 0.03),

              Container(
                alignment: Alignment.center,
                margin: EdgeInsets.symmetric(horizontal: 40),
                child: TextField(
                  controller: _passwordController,
                  decoration: const InputDecoration(
                    labelText: "Password",
                    labelStyle: TextStyle(color: Color(0xFF4c9c1c)),
                    enabledBorder: UnderlineInputBorder(
                      borderSide: BorderSide(color: Color(0xFF4c9c1c)),
                    ),
                    focusedBorder: UnderlineInputBorder(
                      borderSide: BorderSide(color: Color(0xFF4c9c1c)),
                    ),
                  ),
                  obscureText: true,
                ),
              ),

              SizedBox(height: size.height * 0.05),

              Padding(
                padding: EdgeInsets.symmetric(horizontal: 40),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.end,
                  children: [
                    ElevatedButton(
                      onPressed: _isLoading ? null : _submitForm,
                      child: Text(_isLoading ? 'Loading...' : 'Login'),
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Color(0xFFF4CC1D), // Set color to #f4cc1d
                        foregroundColor: Colors.white, // Text color
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                        padding: EdgeInsets.symmetric(horizontal: 60, vertical: 18),
                      ),
                    ),
                  ],
                ),
              ),

              SizedBox(height: size.height * 0.02),
            ],
          ),
        ),
      ),
    );
  }
}
