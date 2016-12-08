package com.icyfruits.overapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by m09-5 on 2016-11-03.
 */

public class Profile extends Activity {
//    private EditText profileID;
//    private EditText profileName;
//    private Button profileBtn;


    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private ProgressDialog progressDialog;
    User user;
    private android.widget.TextView btnLogin;
    private LoginButton loginbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);


    }


//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);
//
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.icyfruits.snsconnect", PackageManager.GET_SIGNATURES
//            );
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }





    @Override
    protected void onResume() {
        super.onResume();
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile","email","user_friends");

        this.btnLogin = (TextView) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(Profile.this);
                progressDialog.setMessage("loading...");
                progressDialog.show();

                loginButton.performClick();
                loginButton.setPressed(true);
                loginButton.invalidate();
                loginButton.registerCallback(callbackManager, mCallBack);
                loginButton.setPressed(false);
                loginButton.invalidate();


            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            progressDialog.dismiss();

            //Appcode
            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),new GraphRequest.GraphJSONObjectCallback(){
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    Log.e("response",response+"");
                    try{
                        user = new User();
                        user.facebookID = object.getString("id").toString();
                        user.email = object.getString("email").toString();
                        user.name = object.getString("name").toString();
                        user.gender = object.getString("gender").toString();
                        PrefUtils.setCurrentUser(user,Profile.this);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    G.login_id = user.name;
                    Toast.makeText(Profile.this,"Welcome "+user.name, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Profile.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            Bundle parameters = new Bundle();
            parameters.putString("fields","id,name,email,gender,birthday");

            request.setParameters(parameters);
            request.executeAsync();



        }

        @Override
        public void onCancel() {
            progressDialog.dismiss();

        }

        @Override
        public void onError(FacebookException error) {

            progressDialog.dismiss();
        }
    };


    //        this.profileBtn = (Button) findViewById(R.id.profileBtn);
//        this.profileName = (EditText) findViewById(R.id.profileName);
//        this.profileID = (EditText) findViewById(R.id.profileID);
//
//        profileBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String id = profileID.getText().toString();
//                String name = profileName.getText().toString();
//                Intent intent = getIntent();
//                intent.putExtra("id",id);
//                intent.putExtra("name",name);
//
//                G.id=id;
//
//                setResult(RESULT_OK,intent);
//
//            finish();
//
//            }
//        });


}
