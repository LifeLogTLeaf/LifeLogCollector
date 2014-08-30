package com.tleaf.lifelog.cardsms;


import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.tleaf.lifelog.model.Sms;

public class SmsReceiver extends BroadcastReceiver {
	public static final String SMS_RECV = "android.provider.Telephony.SMS_RECEIVED";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(SMS_RECV)) {
			// Retrieves a map of extended data from the intent.
			final Bundle bundle = intent.getExtras();

			try {
				if (bundle != null) {

					final Object[] pdusObj = (Object[]) bundle.get("pdus");

					Sms sms; 
					ParseSms parser;
					Map<String, String> resultMap;

					for (int i = 0; i < pdusObj.length; i++) {

						SmsMessage msg = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
						sms = new Sms();
						sms.setAddress(msg.getOriginatingAddress());
						sms.setBody(msg.getMessageBody());
						sms.setDate(msg.getTimestampMillis());

						parser = new ParseSms(context);
						resultMap = parser.isCardCompany(sms.getAddress());
						if(resultMap.get("result").equals("true")) {
							parser.parseCardSms(resultMap.get("cardCompName"), sms);
						}
						
//						int duration = Toast.LENGTH_LONG;
//						Toast toast = Toast.makeText(context, "파싱전\nsenderNum: "+ msg.getOriginatingAddress() + ", message: " + msg.getMessageBody(), duration);
//						toast.show();
					}
			} // bundle is null
		} catch (Exception e) {
			Log.e("SmsReceiver", "Exception smsReceiver" +e);
		}
	}
}

/*String phoneNumber = currentMessage.getDisplayOriginatingAddress();

String senderNum = phoneNumber;
String message = currentMessage.getDisplayMessageBody();

Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);

int duration = Toast.LENGTH_LONG;
Toast toast = Toast.makeText(context, "파싱\nsenderNum: "+ senderNum + ", message: " + message, duration);
toast.show();
 */    					



//Get the object of SmsManager
//	final SmsManager sms = SmsManager.getDefault();

//	public void onReceive(Context context, Intent intent) {

// Retrieves a map of extended data from the intent.
/*	final Bundle bundle = intent.getExtras();

		try {

			if (bundle != null) {

				final Object[] pdusObj = (Object[]) bundle.get("pdus");

				for (int i = 0; i < pdusObj.length; i++) {

					SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage.getDisplayOriginatingAddress();

			        String senderNum = phoneNumber;
			        String message = currentMessage.getDisplayMessageBody();

			        Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);

			        int duration = Toast.LENGTH_LONG;
					Toast toast = Toast.makeText(context, "senderNum: "+ senderNum + ", message: " + message, duration);
					toast.show();

				} // end for loop
           } // bundle is null

		} catch (Exception e) {
			Log.e("SmsReceiver", "Exception smsReceiver" +e);

		}
	}

 */

}