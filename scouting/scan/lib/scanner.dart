import 'package:flutter/material.dart';
import 'package:qr_code_scanner/qr_code_scanner.dart';
import 'dart:io';

class ScanQRCode extends StatefulWidget {
  const ScanQRCode({Key? key}) : super(key: key);

  @override
  State<ScanQRCode> createState() => _ScanQRCodeState();
}

class _ScanQRCodeState extends State<ScanQRCode> {
  final GlobalKey qrKey = GlobalKey(debugLabel: 'QR');
  QRViewController? controller;
  String? _scanCode;

  @override
  void reassemble() {
    super.reassemble();
    if (Platform.isAndroid) {
      controller?.pauseCamera();
    }
    controller?.resumeCamera();
  }

  void _onQRViewCreated(QRViewController controller) {
    this.controller = controller;
    controller.scannedDataStream.listen((scanData) {
      setState(() {
        _scanCode = scanData.code;
      });
    });
  }

  @override
  void dispose() {
    controller?.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('QR Code Scanner')),
      body: Column(
        children: <Widget>[
          Expanded(
            flex: 5,
            child: QRView(key: qrKey, onQRViewCreated: _onQRViewCreated),
          ),
          Expanded(
            flex: 1,
            child: Center(
              child:
                  _scanCode == null
                      ? const SizedBox()
                      : Text(
                        'Scanned Code: $_scanCode',
                        style: const TextStyle(color: Colors.red, fontSize: 18),
                      ),
            ),
          ),
        ],
      ),
    );
  }
}
