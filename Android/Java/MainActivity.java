package com.example.chatjelly;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when the user taps the Send button
     */
    public void sendMessage(View view) throws IOException {
        new RequestAsync(view).execute();
    }
    public class RequestAsync extends AsyncTask<String,String,String> {
        private View _view;
        //final TextView textView;
        EditText editText;
        String message;
        public RequestAsync(View view){
            this._view = view; // this you can use ahead wherever required
            editText = (EditText) findViewById(R.id.editText);
            //textView = (TextView) findViewById(R.id.textView);
            message = editText.getText().toString();
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                //GET Request
                //return RequestHandler.sendGet("https://prodevsblog.com/android_get.php");

                // POST Request
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("username", "Manjeet");
                postDataParams.put("msg", message);

                return com.prodevsblog.httptest.RequestHandler.sendPost("http://192.168.1.13:8080/",postDataParams);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null){
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                //textView.setText("hello, I work");
            }
        }


    }
    }
