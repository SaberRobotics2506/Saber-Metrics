package com.ibxcodecat.frc_scouting.Classes;

import android.content.Context;
import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ibxcodecat.frc_scouting.Activity.DataEntryActivity;
import com.ibxcodecat.frc_scouting.Data.SerializationData;
import com.ibxcodecat.frc_scouting.Data.TeamData;

import org.w3c.dom.CDATASection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileSystem
{
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

        int[] redData = gson.fromJson(redJSONString, int[].class);
        int[] blueData = gson.fromJson(blueJSONString, int[].class);

        return new TeamData(redData, blueData);
    }

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
