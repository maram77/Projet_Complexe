import 'package:flutter/material.dart';

class Background extends StatelessWidget {
  final Widget child;

  const Background({
    Key? key,
    required this.child,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;

    return Container(
      width: size.width,
      height: size.height,
      child: Stack(
        alignment: Alignment.center,
        children: <Widget>[
          Positioned(
            top: 0,
            right: 0,
            child: Image.asset(
              "assets/top1.png",
              width: size.width,
              fit: BoxFit.fill, // Fill the entire width without padding
            ),
          ),
          Positioned(
            top: 0,
            right: 0,
            child: Image.asset(
              "assets/top2.png",
              width: size.width,
              fit: BoxFit.fill,
            ),
          ),
          Positioned(
            top: 50,
            right: 30,
            child: Image.asset(
              "assets/main.png",
              width: size.width * 0.35,
            ),
          ),
          Positioned(
            bottom: 0,
            right: 0,
            child: Image.asset(
              "assets/bottom1.png",
              width: size.width,
              fit: BoxFit.fill,
            ),
          ),
          Positioned(
            bottom: 0,
            right: 0,
            child: Image.asset(
              "assets/bottom2.png",
              width: size.width,
              fit: BoxFit.fill,
            ),
          ),
          child,
        ],
      ),
    );
  }
}