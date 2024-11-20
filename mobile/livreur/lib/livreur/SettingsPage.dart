import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

class SettingsPage extends StatefulWidget {
  const SettingsPage({Key? key}) : super(key: key);

  @override
  _SettingsPageState createState() => _SettingsPageState();
}

class _SettingsPageState extends State<SettingsPage> {
  final _formKey = GlobalKey<FormState>();
  TextEditingController _firstNameController = TextEditingController();
  TextEditingController _lastNameController = TextEditingController();
  TextEditingController _emailController = TextEditingController();
  TextEditingController _phoneController = TextEditingController();
  TextEditingController _passwordController = TextEditingController();
  TextEditingController _confirmPasswordController = TextEditingController();
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _loadUserData();
  }

  Future<void> _loadUserData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String? firstName = prefs.getString('user_firstname');
    String? lastName = prefs.getString('user_lastname');
    String? email = prefs.getString('user_email');
    String? phone = prefs.getString('user_phone');

    _firstNameController.text = firstName ?? '';
    _lastNameController.text = lastName ?? '';
    _emailController.text = email ?? '';
    _phoneController.text = phone ?? '';

    setState(() {
      _isLoading = false;
    });
  }

  Future<void> _updateUserData() async {
    if (_formKey.currentState!.validate()) {
      SharedPreferences prefs = await SharedPreferences.getInstance();
      await prefs.setString('firstName', _firstNameController.text);
      await prefs.setString('lastName', _lastNameController.text);
      await prefs.setString('email', _emailController.text);
      await prefs.setString('phone', _phoneController.text);

      // Here you would typically handle password update separately and securely
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Profile Updated Successfully')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Settings"),
      ),
      body: _isLoading
          ? Center(child: CircularProgressIndicator())
          : SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Form(
            key: _formKey,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                TextFormField(
                  controller: _firstNameController,
                  decoration: InputDecoration(labelText: 'First Name'),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Please enter your first name';
                    }
                    return null;
                  },
                ),
                SizedBox(height: 20),
                TextFormField(
                  controller: _lastNameController,
                  decoration: InputDecoration(labelText: 'Last Name'),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Please enter your last name';
                    }
                    return null;
                  },
                ),
                SizedBox(height: 20),
                TextFormField(
                  controller: _emailController,
                  decoration: InputDecoration(
                    labelText: 'Email',
                    hintText: 'You cannot change the email',
                    fillColor: Colors.grey[300],
                    filled: true,
                  ),
                  enabled: false,
                ),
                SizedBox(height: 20),
                TextFormField(
                  controller: _phoneController,
                  decoration: InputDecoration(labelText: 'Phone Number'),
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Please enter your phone number';
                    }
                    return null;
                  },
                ),
                SizedBox(height: 20),
                TextFormField(
                  controller: _passwordController,
                  decoration: InputDecoration(labelText: 'New Password'),
                  obscureText: true,
                  validator: (value) {
                    if (value != null && value.length < 6) {
                      return 'Password must be at least 6 characters long';
                    }
                    return null;
                  },
                ),
                SizedBox(height: 20),
                TextFormField(
                  controller: _confirmPasswordController,
                  decoration: InputDecoration(labelText: 'Confirm New Password'),
                  obscureText: true,
                  validator: (value) {
                    if (value != _passwordController.text) {
                      return 'Passwords do not match';
                    }
                    return null;
                  },
                ),
                SizedBox(height: 40),
                Center(
                  child: ElevatedButton(
                    onPressed: _updateUserData,
                    child: Text('Update Info'),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  @override
  void dispose() {
    _firstNameController.dispose();
    _lastNameController.dispose();
    _emailController.dispose();
    _phoneController.dispose();
    _passwordController.dispose();
    _confirmPasswordController.dispose();
    super.dispose();
  }
}
