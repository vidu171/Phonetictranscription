package com.example.ashura.phonetictranscription;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<words> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText input = (EditText) findViewById(R.id.editText2);
        final String[] inp = {new String()};

        Button b = (Button) findViewById(R.id.but);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inp[0] = input.getText().toString();
                task thistask = new task();
                thistask.execute(inp[0]);
            }
        });

        ArrayList<words> arrary = new ArrayList<>();


    }

    public class task extends AsyncTask<String, Void, ArrayList<words>>{

        private ProgressDialog dialog;

        @Override
        protected ArrayList<words> doInBackground(String... params) {



            InputStream input = getResources().openRawResource(R.raw.total);
            InputStreamReader reader = new InputStreamReader(input, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder output = new StringBuilder();

            try {
                String Line = bufferedReader.readLine();
                while (Line!=null){
                    output.append(Line);
                    Line = bufferedReader.readLine();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }

            try {
                JSONArray array = new JSONArray(output.toString());
                for(int i =0; i < array.length(); i++) {
                    JSONObject root = array.getJSONObject(i);
                    String english = root.getString("word");
                    String translation = root.getString("translation");
                    arr.add(new words(english, translation));

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            StringBuilder out_string = new StringBuilder();
            String s = params[0];
            String[] inputs = params[0].split(" ");
            for (int i = 0; i<inputs.length;i++){
                for (int j = 0; j<arr.size(); j++){

                    if(inputs[i].equals(arr.get(j).English)){
                        out_string.append(arr.get(i).Phonetic+ " ");

                    }
                }

            }

            Log.w("  ", out_string.toString() );
            Toast.makeText(getApplicationContext(),out_string.toString(),Toast.LENGTH_LONG).show();

            return arr;
        }

        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("loading..");
            dialog.setCancelable(false);
            dialog.show();

            super.onPreExecute();
        }
        protected void onPostExecute(ArrayList<words> data){
            dialog.dismiss();
        }

    }
    public  void addarr1(ArrayList<words>arr1){

    }

}
