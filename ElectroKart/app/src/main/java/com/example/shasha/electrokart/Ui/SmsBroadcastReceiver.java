package com.example.shasha.electrokart.Ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by shasha on 13-03-2016.
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";

    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String smsBody = smsMessage.getMessageBody().toString();
                String address = smsMessage.getOriginatingAddress();
                if (address != " "){
                    if((smsBody.contains("withdrawn")|| smsBody.contains("debited")|| smsBody.contains("debit"))&&smsBody.contains(" A/c")){
                        smsMessageStr += "SMS From: " + address + "\n";
                        smsMessageStr += smsBody + "\n";
                    }

                }

            }
            Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();

            //this will update the UI with message
            TransactionBillsActivity inst = TransactionBillsActivity.instance();
            inst.updateList(smsMessageStr);
        }
    }

}