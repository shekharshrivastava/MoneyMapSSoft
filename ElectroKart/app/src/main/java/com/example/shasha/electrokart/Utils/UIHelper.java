package com.example.shasha.electrokart.Utils;

import android.app.Dialog;
import android.content.Context;

import com.sdsmdg.tastytoast.TastyToast;

//import com.sdsmdg.tastytoast.TastyToast;

/**
 * Created by shasha on 21-07-2016.
 */
public class UIHelper {

    public boolean displayDialog (Context context,int res){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(res);
        dialog.show();
        return true;
    }

    public  void generateToast(Context context , String message,int length ,int type ){
        TastyToast.makeText(context, message, length,type);
    }
}
