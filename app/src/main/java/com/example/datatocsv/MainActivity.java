package com.example.datatocsv;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the current time in the desired format
                String pattern = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String currentTime = simpleDateFormat.format(new Date());

                // create a file to store the button click times
                File file = new File(getFilesDir(), "button_clicks.txt");
                try {
                    InputStream inputStream = openFileInput("button_clicks.txt");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }

                    reader.close();
                    inputStream.close();

                    // The contents of the file are now in the stringBuilder variable
                    String fileContents = stringBuilder.toString();
                    Log.d("MainActivity", "Contents of button_clicks.txt: " + fileContents);
                    String filename = "myfile.txt";
                    FileOutputStream outputStream;

                    try {
                        outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                        outputStream.write(fileContents.getBytes());
                        outputStream.close();
                        Log.d("MainActivity", "Data saved to file: " + getFilesDir() + "/" + filename);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    // create a FileWriter to write to the file
                    FileWriter writer = new FileWriter(file, true);

                    // append the current time to the file
                    writer.write(currentTime + "\n");

                    // close the FileWriter
                    writer.close();

                    // log the success message
                    Log.d(TAG, "Button click time saved to file: " + file.getAbsolutePath());

                } catch (IOException e) {
                    // log the error message
                    Log.e(TAG, "Error saving button click time to file", e);
                }
            }
        });
    }
}
