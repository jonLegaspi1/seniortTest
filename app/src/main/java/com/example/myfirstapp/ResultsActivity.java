package com.example.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class ResultsActivity extends AppCompatActivity {

    private static final String FILE_NAME = "results.csv";
    TextView mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        mEditText = findViewById(R.id.edit_text);

    }

    public void load(View v){

        Button resultButton = (Button) findViewById(R.id.result);
        resultButton.setVisibility(View.INVISIBLE);
        FileInputStream fis = null;

        try{
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text = "";

            while ((text = br.readLine()) != null){
                sb.append(text).append("\n");
            }

            mEditText.setText(sb.toString());
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    //button.setVisibility(View.GONE);
}
