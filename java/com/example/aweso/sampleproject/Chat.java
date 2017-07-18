package com.example.aweso.sampleproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Chat extends AppCompatActivity {

    private TextView username;
    private TextView chatbox;
    private EditText message;

    private String uName;
    private String uMessage;

    private String stringToSend;
    public String uri = "http://" + UserProfile.ipAddress + ":8080/MobileChatroom_Server/webresources/com.server.chatroom.chatroom/send?username=" + UserProfile.currentUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        username = (TextView) findViewById(R.id.lbl_Username);

        username.setText("Chatting as : " + UserProfile.currentUserName);
    }

    public void buttonOnClick(View v)
    {
        message = (EditText) findViewById(R.id.txt_Chat);
        username = (TextView) findViewById(R.id.lbl_Username);
        chatbox = (TextView) findViewById(R.id.textView2);

        Toast toast = new Toast(getApplicationContext());
        toast.makeText(Chat.this, "Sent", toast.LENGTH_SHORT).show();

        uMessage = message.getText().toString();
        chatbox.append("You : " + uMessage + "\n");
        message.setText("");

        stringToSend = "{ \"message\" : \"" + uMessage + "\" }";

        new ConfirmActivity().execute();
    }

    public String sendJsonObject (String stringToParse, String urlQuery)
    {
        try {
            URL url = new URL(urlQuery);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");

            OutputStream os = conn.getOutputStream();
            os.write(stringToParse.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                return ("Failed : HTTP error code : " + conn.getResponseCode());
                //throw new Exception();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            String concatOutput = "";
            while ((output = br.readLine()) != null) {
                concatOutput += output;
            }

            conn.disconnect();

            return concatOutput;

        } catch (Exception e) {
            //return e.getMessage();
            return "error";
        }
    }

    class ConfirmActivity extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... params) {

            sendJsonObject(stringToSend, uri);

            return "";
        }
    }
}
