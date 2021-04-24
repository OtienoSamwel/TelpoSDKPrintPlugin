package com.stanleyidesis.cordova.plugin;
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
import com.telpo.tps550.api.printer.ThermalPrinter;
import com.telpo.tps550.api.util.StringUtil;
import com.telpo.tps550.api.util.SystemUtil;


public class ToastyPlugin extends CordovaPlugin {
  MyHandler handler;
	private int leftDistance = 0;
	private int lineDistance = 0;
  private final int PRINTCONTENT = 9;

  private class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			
			case PRINTCONTENT:
				new contentPrintThread().start();
				break;
			default:
				Toast.makeText(cordova.getActivity().getApplicationContext(), "Print Error!", Toast.LENGTH_LONG).show();
				break;
			}
		}
	}

  private class contentPrintThread extends Thread {
		@Override
		public void run() {
			super.run();
			try {
				ThermalPrinter.start(cordova.getActivity().getApplicationContext());
				ThermalPrinter.reset();
				ThermalPrinter.setAlgin(ThermalPrinter.ALGIN_LEFT);
				ThermalPrinter.setLeftIndent(0);
				ThermalPrinter.setLineSpace(0);

				ThermalPrinter.setFontSize(2);
				ThermalPrinter.enlargeFontSize(2, 2);

				ThermalPrinter.setGray(7);
				ThermalPrinter.addString("Printing worked!!!");
				ThermalPrinter.printString();
				ThermalPrinter.walkPaper(50);

        Log.d("print_test","Trying tp print::!!");
        // Toast toast = Toast.makeText(cordova.getActivity(),"Trying to print::!!!",Toast.LENGTH_LONG);
        // toast.show();

			} catch (Exception e) {
				e.printStackTrace();
				// Result = e.toString();
				// if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
				// 	nopaper = true;
				// } else if (Result.equals("com.telpo.tps550.api.printer.OverHeatException")) {
				// 	//handler.sendMessage(handler.obtainMessage(OVERHEAT, 1, 0, null));
				// } else {
				// 	//handler.sendMessage(handler.obtainMessage(PRINTERR, 1, 0, null));
				// }
        Log.d("print_test","Trying tp!!");
        // Toast toast = Toast.makeText(cordova.getActivity(),"an error occured",Toast.LENGTH_LONG);
        // toast.show();

			} finally {
				//handler.sendMessage(handler.obtainMessage(CANCELPROMPT, 1, 0, null));
				// if (nopaper){
				// 	//handler.sendMessage(handler.obtainMessage(NOPAPER, 1, 0, null));
				// 	nopaper = false;
				// 	return;
				// }

        // Toast toast = Toast.makeText(cordova.getActivity(),"always execute",Toast.LENGTH_LONG);
        // toast.show();

				ThermalPrinter.stop(cordova.getActivity().getApplicationContext());
			}
		}
	}

  @Override
  public boolean execute(String action, JSONArray args,
    final CallbackContext callbackContext) {
      // Verify that the user sent a 'show' action
      if (!action.equals("show")) {
        callbackContext.error("\"" + action + "\" is not a recognized action.");
        return false;
      }

      String message;
      String duration;
      try {
        JSONObject options = args.getJSONObject(0);
        message = options.getString("message");
        duration = options.getString("duration");
      } catch (JSONException e) {
        callbackContext.error("Error encountered: " + e.getMessage());
        return false;
      }

      Toast toast = Toast.makeText(cordova.getActivity(), message,Toast.LENGTH_LONG);
      // Display toast
      toast.show();


      handler = new MyHandler();
      handler.sendMessage(handler.obtainMessage(PRINTCONTENT, 1, 0, null));
      return true;
  }
}