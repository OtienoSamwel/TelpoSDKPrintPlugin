package com.mj.cordova.plugin;
// The native Toast API
import android.widget.Toast;
// Cordova-required packages
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.printer.UsbThermalPrinter;
import com.telpo.tps550.api.util.StringUtil;
import com.telpo.tps550.api.util.SystemUtil;


public class PrintingPlugin extends CordovaPlugin {
	private int leftDistance = 0;
	private int lineDistance = 0;
	private final int PRINTCONTENT = 9;


  @Override
  public boolean execute(String action, JSONArray args,
    final CallbackContext callbackContext) {
      // Verify that the user sent a 'show' action

	  UsbThermalPrinter mUsbThermalPrinter = new UsbThermalPrinter(cordova.getActivity());

      if (!action.equals("print")) {
        callbackContext.error("\"" + action + "\" is not a recognized action.");
        return false;
      }

      String message;
      //String duration;
      try {
        JSONObject options = args.getJSONObject(0);
        message = options.getString("message");
        //duration = options.getString("duration");
      } catch (JSONException e) {
        callbackContext.error("Error encountered: " + e.getMessage());
        return false;
      }

      Toast toast1 = Toast.makeText(cordova.getActivity(), message,Toast.LENGTH_LONG);
      // Display toast
      toast1.show();

	  try {

		Toast toast2 = Toast.makeText(cordova.getActivity(), "Trying to print",Toast.LENGTH_LONG);
		toast2.show();


		mUsbThermalPrinter.reset();
		mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);
		mUsbThermalPrinter.setLeftIndent(leftDistance);
		mUsbThermalPrinter.setLineSpace(lineDistance);
		mUsbThermalPrinter.setTextSize(40);
		//mUsbThermalPrinter.setHighlight(true);
		mUsbThermalPrinter.setGray(7);
		mUsbThermalPrinter.addString("This is working now");
		mUsbThermalPrinter.printString();
		mUsbThermalPrinter.walkPaper(20);

		Log.d("print_test","Trying tp print::!!");
		
	} catch (Exception e) {
		e.printStackTrace();
		Log.d("print_test","Trying tp!!");

		Toast toast3 = Toast.makeText(cordova.getActivity(), "excemption",Toast.LENGTH_LONG);
		toast3.show();

	} finally {
		//ThermalPrinter.stop(cordova.getActivity().getApplicationContext());

		Toast toast4 = Toast.makeText(cordova.getActivity(), "finally",Toast.LENGTH_LONG);
		toast4.show();
	}
      return true;
  }
}