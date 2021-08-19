package com.mj.cordova.plugin;
// The native Toast API
// Cordova-required packages
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Toast;
import android.text.Html;
import android.util.Log;


import com.telpo.tps550.api.printer.UsbThermalPrinter;

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
		String header;
		String title;
		try {
			JSONObject options = args.getJSONObject(0);
			
			header = options.getString("header");
			title = options.getString("title");
			message = options.getString("message");

		} catch (JSONException e) {
			callbackContext.error("Error encountered: " + e.getMessage());
			return false;
		}

		try {
			//SECTION ONE
			mUsbThermalPrinter.reset();
			mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);
			mUsbThermalPrinter.setLeftIndent(leftDistance);
			mUsbThermalPrinter.setLineSpace(lineDistance);
			mUsbThermalPrinter.setTextSize(20);
			//mUsbThermalPrinter.setHighlight(true);
			mUsbThermalPrinter.setGray(5);
			mUsbThermalPrinter.setBold(true);

			mUsbThermalPrinter.addString(Html.fromHtml(header).toString());
			mUsbThermalPrinter.printString();

			//END SECTION ONE
			//SECTION TWO
			mUsbThermalPrinter.reset();
			mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);
			mUsbThermalPrinter.setLeftIndent(leftDistance);
			mUsbThermalPrinter.setLineSpace(lineDistance);
			mUsbThermalPrinter.setTextSize(20);
			//mUsbThermalPrinter.setHighlight(true);
			mUsbThermalPrinter.setGray(5);
			mUsbThermalPrinter.setBold(false);

			mUsbThermalPrinter.addString(Html.fromHtml(title).toString());
			mUsbThermalPrinter.printString();

			//END SECTION TWO
			//START SECTION THREE

			mUsbThermalPrinter.reset();
			mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);
			mUsbThermalPrinter.setLeftIndent(leftDistance);
			mUsbThermalPrinter.setLineSpace(lineDistance);
			mUsbThermalPrinter.setTextSize(20);
			//mUsbThermalPrinter.setHighlight(true);
			mUsbThermalPrinter.setGray(5);
			mUsbThermalPrinter.setBold(false);

			mUsbThermalPrinter.addString(Html.fromHtml(message).toString());
			mUsbThermalPrinter.printString();
			//END SECTION THREE
			mUsbThermalPrinter.walkPaper(20);

			Log.d("print_test","Trying tp print::!!");

		} catch (Exception e) {

			Log.d("print_test","Trying tp!!");

			String Result = e.toString();

			// Toast mm = Toast.makeText(cordova.getActivity(),Result,Toast.LENGTH_LONG);
			// mm.show();

			if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {

				Toast noPaper = Toast.makeText(cordova.getActivity(), "No Papers",Toast.LENGTH_LONG);
				noPaper.show();
			} else if (Result.equals("com.telpo.tps550.api.printer.OverHeatException")) {
				Toast overheat = Toast.makeText(cordova.getActivity(), "Overheat excemption",Toast.LENGTH_LONG);
				overheat.show();
			}else{
				Toast otherExcemptions = Toast.makeText(cordova.getActivity(), "Low battery. Put device on charger to use the printer",Toast.LENGTH_LONG);
				otherExcemptions.show();
			}

		}
		return true;
	}
}