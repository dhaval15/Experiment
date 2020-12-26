import 'dart:convert';

import 'package:flutter/services.dart';
import 'package:flutter/material.dart';

const platform = const MethodChannel('com.experiment.success/result');
void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  platform.setMethodCallHandler(_receiveFromHost);
}

Future<void> _receiveFromHost(MethodCall call) async {
  print(call.method);
  try {
    if (call.method == "add") {
      final String data = call.arguments;
      final jData = await jsonDecode(data);
      final result = jData['x'] + jData['y'];
      platform.invokeMethod('add', result);
    }
  } on PlatformException catch (error) {
    print(error);
  }
}
