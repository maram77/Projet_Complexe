import 'package:flutter/material.dart';
import 'package:livreur/livreur/Deliveries.dart';
import 'package:livreur/livreur/SettingsPage.dart';
import 'package:shared_preferences/shared_preferences.dart';

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  int _selectedIndex = 0;

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  String _firstName = '';
  String _email = '';
  String _lastname='';

  @override
  void initState() {
    super.initState();
    _getUserDataFromSharedPreferences();
  }

  void _getUserDataFromSharedPreferences() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String firstName = prefs.getString('user_firstname') ?? '';
    String lastname = prefs.getString('user_lastname') ?? '';
    String email = prefs.getString('user_email') ?? '';

    setState(() {
      _firstName = firstName;
      _email = email;
      _lastname=lastname;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Fresh Market"),
      ),
      drawer: Drawer(
        child: ListView(
          padding: EdgeInsets.zero,
          children: <Widget>[
            UserAccountsDrawerHeader(
              decoration: const BoxDecoration(
                color: Color(0xFF4c9c1c) ,
              ),
              accountName: Text(_firstName+" "+_lastname),
              accountEmail: Text(_email),
              currentAccountPicture: CircleAvatar(
                backgroundColor: Colors.white,
                child: Icon(
                  Icons.person,
                  color: Color(0xFF4c9c1c),
                ),
              ),
            ),
            ListTile(
              title: Text('Deliveries'),
              leading: Icon(Icons.delivery_dining),
              onTap: () {
                Navigator.pop(context); // Close the drawer
                _onItemTapped(0);
              },
            ),
            ListTile(
              title: Text('Profile settings'),
              leading:Icon(Icons.manage_accounts,),
              onTap: () {
                Navigator.pop(context); // Close the drawer
                _onItemTapped(1);
                // Handle messages action
              },
            ),
            ListTile(
              title: Text('Sign Out'),
              leading: Icon(Icons.exit_to_app),
              onTap: () {
                _signOut();
                Navigator.pushNamed(context, "/login");
              },
            ),
          ],
        ),
      ),
      body: _buildPage(),
    );
  }

  Widget _buildPage() {
    switch (_selectedIndex) {
      case 0:
        return Deliveries();
      case 1:
        return SettingsPage();
      default:
        throw UnimplementedError('no widget for $_selectedIndex');
    }
  }

  void _signOut() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.clear(); // Clear user data
    Navigator.pushNamed(context, "/login");
  }
}