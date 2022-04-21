package com.ibxcodecat.frc_scouting.Classes;

import android.content.Context;
import android.os.Environment;

import com.google.gson.Gson;

import org.json.JSONObject;
import org.json.JSONException;

import com.google.gson.JsonObject;
import com.ibxcodecat.frc_scouting.Activity.DataEntryActivity;
import com.ibxcodecat.frc_scouting.Data.SerializationData;
import com.ibxcodecat.frc_scouting.Data.TeamData;

import org.w3c.dom.CDATASection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class FileSystem {

    public String ReadDeviceID(Context context)
    {
        //Checking the availability state of the External Storage.
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            //If it isn't mounted - we can't write into it.
            return "";
        }

        try
        {
            File file = new File(context.getExternalFilesDir(null), "ID.txt");

            FileInputStream in = new FileInputStream(file);

            int i = -1;

            StringBuffer buffer = new StringBuffer();

            while((i = in.read()) != -1)
            {
                buffer.append((char)i);
            }

            in.close();

            String output = buffer.toString().substring(3);
            return output;
        }
        catch(IOException ex)
        {
            ex.printStackTrace();

            String exceptionString = "";

            for(StackTraceElement element : ex.getStackTrace())
            {
                exceptionString += element;
            }

            return "There was a problem reading device data. Please contact Nathan or Dominic\n\nAre you reading from the wrong location again Nathan? Try: " + context.getExternalFilesDir(null) + "\n\n" + exceptionString;
        }

    }

    //<summary>
    //Responsible for Reading the JSON assets ported into the app prior to compilation and parsing
    //the JSON data using GSON into int[].class objects to construct a TeamData object
    //</summary>
    //<returns>A TeamData object</returns>
    public TeamData ReadJSONFromAssets(Context context, String redFile, String blueFile)
    {
        String redJSONString, blueJSONString;

        try {

            InputStream redInputStream = context.getAssets().open(redFile);
            int redSize = redInputStream.available();
            byte[] redBuffer = new byte[redSize];
            redInputStream.read(redBuffer);
            redInputStream.close();

            InputStream blueInputStream = context.getAssets().open(blueFile);
            int blueSize = blueInputStream.available();
            byte[] blueBuffer = new byte[blueSize];
            blueInputStream.read(blueBuffer);
            blueInputStream.close();

            redJSONString = new String(redBuffer, "UTF-8");
            blueJSONString = new String(blueBuffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Gson gson = new Gson();

        Object redObj = gson.fromJson(redJSONString, Object.class);
        Object blueObj = gson.fromJson(blueJSONString, Object.class);

        return new TeamData(redObj, blueObj);
    }

    //<summary>
    //Converts the SerializationData object to a JSON String using GSON and writes it to the disk
    //</summary>
    //<returns>void</returns>
    public boolean WriteGSON(SerializationData serializationData, DataEntryActivity context)
    {
        Gson gson = new Gson();
        String data = gson.toJson(serializationData);

        //Checking the availability state of the External Storage.
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            //If it isn't mounted - we can't write into it.
            return false;
        }

        //Create a new file that points to the root directory, with the given name:
        File file = new File(context.getExternalFilesDir(null), serializationData.getTeamNumber() + "-" + serializationData.getMatchNumber() + ".scout");

        //This point and below is responsible for the write operation
        FileOutputStream outputStream;
        try {
            file.createNewFile();

            //second argument of FileOutputStream constructor indicates whether
            //to append or create new file if one exists
            outputStream = new FileOutputStream(file, true);

            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
