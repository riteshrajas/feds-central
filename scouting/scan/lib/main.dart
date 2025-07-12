//! You do not need to change this file, unless you wanna add Hive Box's
import 'package:flutter/material.dart';

import 'homepage.dart';

void main() {
  addHiveBoxes();
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(home: Homepage());
  }
}

void addHiveBoxes() {
  //* Add your Hive boxes here if needed
  // Hive.openBox('your_box_name');
}

