import 'package:flutter/widgets.dart';
import 'package:mobile_scanner/mobile_scanner.dart';

class BarcodeScannerListView extends StatefulWidget {
  const BarcodeScannerListView({super.key});

  @override
  State<BarcodeScannerListView> createState() => _BarcodeScannerListViewState();
}

class _BarcodeScannerListViewState extends State<BarcodeScannerListView> {
  final MobileScannerController controller = MobileScannerController(torchEnabled: true,);
  final List<Barcode> barcodes = []; 

  void _handleBarcode(BarcodeCapture capture) {
    final List<Barcode> newBarcode = capture.barcodes;
     
     for (final barcode in newBarcodes) {
      if (!barcodes.contains(barcode)) {
        setState(() {
          barcodes.add(barcode);
        });
      }
    }
  }



Widget _buildBarcodeListView() {
    if (barcodes.isEmpty) {
      return const Center(
        child: Text(
          'No barcode found',
          style: TextStyle(
            color: Color.fromARGB(255, 255, 255, 255,
            fontSize: 20,
            )
            )
        ),
        )
    }

    return ListView.builder(
      itemCount: barcodes.length, 
      itemBuilder: (context, index) {
        return Padding(
          padding: const EdgeInsets.all(8.0),
          child: Text(
            barcodes[index].rawValue ?? 'Unknown',
            style: const TextStyle(
              color: Color.fromARGB(255, 255, 255, 255),
              fontSize: 20,
            ),
          )
        );
  
    }
    );

