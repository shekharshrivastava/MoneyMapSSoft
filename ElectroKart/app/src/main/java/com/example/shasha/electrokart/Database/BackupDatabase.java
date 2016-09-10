package com.example.shasha.electrokart.Database;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by shasha on 13-03-2016.
 */
public class BackupDatabase {


    public void importDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                String currentDBPath = "//data//" + "com.example.shasha.electrokart"
                        + "//databases//" + "moneymap.sqlite";
                String backupDBPath = sd+"/database_copy..sqlite"; // From SD directory.
                File backupDB = new File(data, currentDBPath);
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

            }
        } catch (Exception e) {

          e.printStackTrace();

        }
    }

    public void exportDB(String getPackageName) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + getPackageName
                        + "//databases//" + "moneymap.sqlite";
                String backupDBPath = sd+"/database_copy.sqlite";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void backupDatabase() throws IOException {
        //Open your local db as the input stream
        String inFileName = "/data/data/com.example.shasha.electrokart/databases/moneymap.sqlite";
        File dbFile = new File(inFileName);
        FileInputStream fis = new FileInputStream(dbFile);

        String outFileName = Environment.getExternalStorageDirectory()+"/MYDB.db";
        //Open the empty db as the output stream
        OutputStream output = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer))>0){
            output.write(buffer, 0, length);
        }
        //Close the streams
        output.flush();
        output.close();
        fis.close();
    }
}
