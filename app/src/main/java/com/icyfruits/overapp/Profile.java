package com.icyfruits.overapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by m09-5 on 2016-11-03.
 */

public class Profile extends AppCompatActivity {


    private EditText profileID;
    private EditText profileName;
    private Button profileBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        this.profileBtn = (Button) findViewById(R.id.profileBtn);
        this.profileName = (EditText) findViewById(R.id.profileName);
        this.profileID = (EditText) findViewById(R.id.profileID);

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = profileID.getText().toString();
                String name = profileName.getText().toString();
                Intent intent = getIntent();
                intent.putExtra("id",id);
                intent.putExtra("name",name);

                G.id=id;

                setResult(RESULT_OK,intent);

            finish();

            }
        });

    }



}