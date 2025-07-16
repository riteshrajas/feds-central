import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'navigator.dart';

class Homepage extends StatelessWidget {
  const Homepage({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        backgroundColor: const Color.fromARGB(255, 0, 0, 0),
        drawer: const NavBar(),
        appBar: AppBar(
          title: Center(
            child: ShaderMask(
              shaderCallback:
                  (bounds) => const LinearGradient(
                    colors: [Colors.red, Colors.blue],
                    begin: Alignment.topLeft,
                    end: Alignment.bottomRight,
                  ).createShader(bounds),
              child: Text(
                'Scout Ops Scan',
                style: GoogleFonts.museoModerno(
                  fontSize: 30,
                  fontWeight: FontWeight.w500,
                  color: Colors.white,
                ),
              ),
            ),
          ),
          backgroundColor: const Color.fromARGB(255, 0, 0, 0),
        ),

        // Main content of the homepage
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              const SizedBox(height: 20), // Space at the top
              scanner(context), // Call the scanner widget
            ],
          ),
        ),
      ),
    );
  }

  Widget scanner(BuildContext context) {
    return Container(
      height: 600, // Added height specification
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(20), // Added border radius
      ),
      child: ClipRRect(
        borderRadius: BorderRadius.circular(20),
       
      ),
    );
  }
}
