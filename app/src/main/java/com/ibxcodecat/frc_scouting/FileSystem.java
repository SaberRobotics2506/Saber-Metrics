package com.ibxcodecat.frc_scouting;

import android.os.Environment;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;

public class FileSystem
{

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
        File file = new File(context.getExternalFilesDir(null), "test.txt");

        //This point and below is responsible for the write operation
        FileOutputStream outputStream;
        try {
            //file.createNewFile();
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
