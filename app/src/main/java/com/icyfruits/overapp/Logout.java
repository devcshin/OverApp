package com.icyfruits.overapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by DEV.C on 2016-12-05.
 */

public class Logout extends AppCompatActivity {
    private ImageView profileImage;
    private TextView btnLogout;
    int a;

    Bitmap bitmap;
    User user;
    private TextView showname;
    private TextView showgender;
    private TextView showmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout);
        this.showmail = (TextView) findViewById(R.id.showmail);
        this.showgender = (TextView) findViewById(R.id.showgender);
        this.showname = (TextView) findViewById(R.id.showname);
        this.btnLogout = (TextView) findViewById(R.id.btnLogout);
        this.profileImage = (ImageView) findViewById(R.id.profileImage);
        user = PrefUtils.getCurrentUser(Logout.this);

        //fetching Image
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                URL imageURL = null;
                try {
//                    String s = getResources().getString(R.string.facebook_app_id);
                    imageURL = new URL(("https://graph.facebook.com/" + user.facebookID + "/picture?type=large"));

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                profileImage.setImageBitmap(bitmap);
                showgender.setText(user.gender);
                showname.setText(user.name);
                showmail.setText(user.email);
            }
        }.execute();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.clearCurrentUser(Logout.this);

                //logout by calling forward method
                LoginManager.getInstance().logOut();

                Intent i = new Intent(Logout.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }//oncreate

}//class