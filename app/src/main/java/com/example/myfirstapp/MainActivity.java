package com.example.myfirstapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String FILE_NAME = "results.csv";
    //private static int SPLASH_TIME_OUT = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public String LoadData(String inFile){
        String contents = "";

        try {
            InputStream stream = getAssets().open(inFile);
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            contents = new String(buffer);
        } catch (IOException e){

        }
        return contents;
    }
    public String LoadData2(String inFile){
        String contents = "";
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try{
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open(inFile)));
            reader.readLine();

            while((contents = reader.readLine()) != null){
                sb.append(contents).append("\n");
                for (int i = 0; i < 3; i++){ reader.readLine(); }
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        contents = sb.toString();
        Log.i(TAG, contents);
        return contents;
    }
    public static int findIndex(String kmer) {
        String dmer = "";

        for (int i = 0; i < kmer.length() - 1; i++) {
            if (kmer.charAt(i) == 'A') dmer = '0' + dmer;
            else if (kmer.charAt(i) == 'G') dmer = '1' + dmer;
            else if (kmer.charAt(i) == 'C') dmer = '2' + dmer;
            else if (kmer.charAt(i) == 'T') dmer = '3' + dmer;
        }

        int index = 0;
        double exp;
        int doodle;

        for (int i = dmer.length() - 1; i >= 0; i--) {
            doodle = Character.getNumericValue(dmer.charAt(i));
            exp = Math.pow(4, i);
            index += doodle * exp;
        }

        return index;
    }
    public void openResultActivity(){
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
    }
    public void onButtonTap(View v) throws FileNotFoundException {
        Toast myToast = Toast.makeText(getApplicationContext(), "Performing Algorithm!", Toast.LENGTH_LONG);
        boolean test[][] = new boolean[64][4];

        //String s = "TCGCACTCAACGCCCTGCATATGACAAGACAGAATC";
        String s = LoadData2("test.txt");
        int num = s.length();
        int k_length = 4;
        String  substr = "";
        String output = "";
        char nindex;
        int index;
        for (int i = 0; i < num - k_length + 1; i++) {
            substr = s.substring(i,i+k_length);
            //System.out.println(substr);
            //Log.i(TAG, substr);
            //println(substr);
            nindex = substr.charAt(substr.length() - 1);
            index = findIndex(substr);
            if (nindex == 'A') test[index][0] = true;
            if (nindex == 'G') test[index][1] = true;
            if (nindex == 'C') test[index][2] = true;
            if (nindex == 'T') test[index][3] = true;
        }
        for (int i = 0; i < 64; i++) {
            Log.i(TAG,test[i][0] + "\t" + test[i][1] + " \t" + test[i][2] + "\t" + test[i][3]);
        }
        output = Arrays.deepToString(test);
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(output.getBytes());

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        myToast.show();
        openResultActivity();
    }
}
