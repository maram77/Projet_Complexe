import 'package:animated_splash_screen/animated_splash_screen.dart';
import 'package:flutter/material.dart';
import 'package:livreur/livreur/Deliveries.dart';
import 'package:livreur/livreur/HomePage.dart';
import 'package:livreur/livreur/LoginPage.dart';


void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Spring Boot App',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      initialRoute: '/',
      routes: {
        '/': (context) => AnimatedSplashScreen(
          splash: Image.asset('assets/logo.png'),
          nextScreen: LoginPage(),
          splashTransition: SplashTransition.fadeTransition,
          duration: 3000,
        ),
        '/login': (context) => LoginPage(),
        '/home': (context) => HomePage(),
      },
    );
  }
}
