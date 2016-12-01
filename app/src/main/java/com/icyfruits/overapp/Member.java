package com.icyfruits.overapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by m09-5 on 2016-11-23.
 */

public class Member extends AppCompatActivity {

    private android.widget.EditText textid;
    private android.widget.EditText textpw;
    private android.widget.EditText textemail;
    private android.widget.EditText texthp;
    private android.widget.Button btnsubmit;
    private android.widget.Button btncancel;

    String id, pw, mail, hp, data;
    String server = "http://devc2016.dothome.co.kr/Android/meminstdb.php";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member);
        this.btncancel = (Button) findViewById(R.id.btn_cancel);
        this.btnsubmit = (Button) findViewById(R.id.btn_submit);
        this.texthp = (EditText) findViewById(R.id.text_hp);
        this.textemail = (EditText) findViewById(R.id.text_email);
        this.textpw = (EditText) findViewById(R.id.text_pw);
        this.textid = (EditText) findViewById(R.id.text_id);


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        id = textid.getText().toString();
                        pw = textpw.getText().toString();
                        mail = textemail.getText().toString();
                        hp = texthp.getText().toString();

                        try {
                            URL url = new URL(server);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("POST");
                            connection.setDoOutput(true);
                            connection.setDoInput(true);
                            connection.setUseCaches(false);

                            data = "id=" + id + "&pw=" + pw + "&mail=" + mail + "&hp=" + hp;

                            OutputStream os = connection.getOutputStream();
                            os.write(data.getBytes());
                            os.flush();
                            os.close();

                            //서버에코 // w지금은 생략

                            InputStream is = connection.getInputStream();
                            InputStreamReader isr = new InputStreamReader(is);
                            BufferedReader reader = new BufferedReader(isr);

                            final StringBuffer buffer = new StringBuffer();

                            String line = reader.readLine();
                            while (line != null) {
                                buffer.append(line + "\n");
                                line = reader.readLine();
                            }


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textid.setText("");
                                    textpw.setText("");
                                    textemail.setText("");
                                    texthp.setText("");


                                    Toast.makeText(Member.this, buffer.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });


                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();

            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


}

