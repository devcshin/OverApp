package com.icyfruits.overapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView profileImage;
    private TextView btnLogout;

    int a;

    Bitmap bitmap;

    User user;
    private TextView showname;
    private TextView showgender;
    private TextView showmail;

    FragmentTabHost tabHost;
    ViewPager pager;
    PageAdapter adapter;
//    ImageView image;
    CircleImageView image;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Intent intent, intent2, intentProfile, member;
    TextView text1, text2;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private ProgressDialog progressDialog;

    private android.widget.TextView btnLogin;
    private LoginButton loginbutton;

    //버튼달자
    ActionBarDrawerToggle drawerToggle;
    private android.widget.TabWidget tabs;
    private android.widget.FrameLayout tabcontent;
    private ViewPager viewpager;
    private FragmentTabHost tabhost;
    private NavigationView navidrawer;
    private DrawerLayout drawerlayout;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DISK DATA 가져오기
        SharedPreferences pref = getSharedPreferences("count", MODE_PRIVATE);
        G.count = pref.getInt("key", 0);
        G.login_id = pref.getString("id", "");
        /////////////////////////////////
//        swipeRefreshLayout.findViewById(R.id.listview2);
//        swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);


        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.icyfruits.overapp", PackageManager.GET_SIGNATURES
            );
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        this.drawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.navidrawer = (NavigationView) findViewById(R.id.navi_drawer);
        this.tabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        this.tabcontent = (FrameLayout) findViewById(android.R.id.tabcontent);
        this.tabs = (TabWidget) findViewById(android.R.id.tabs);
        View header = navidrawer.getHeaderView(0);

//        image = (ImageView) header.findViewById(R.id.image);
        image = (CircleImageView)header.findViewById(R.id.image);

        intent = new Intent(this, SecondActivity.class);
        intent2 = new Intent(this, ThirdActivity.class);
        member = new Intent(this, Member.class);
        intentProfile = new Intent(this, Profile.class);


        text1 = (TextView) header.findViewById(R.id.text1);
        text2 = (TextView) header.findViewById(R.id.text2);

        int a;


        //왜 못받아올까
        if (G.login_id.equals("")) {
            text1.setText("안녕하세요");
        } else {

            text1.setText(G.login_id);
        }


        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        //탭버튼(탭스펙) 추가작업을 위한 셋업명령(메소드)
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);


        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Post BOX"), DummyFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Letter"), DummyFragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Calendar"), DummyFragment.class, null);

        pager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new PageAdapter(getSupportFragmentManager());
        //두개 만들었다

        pager.setAdapter(adapter);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
//
//                String s="tab1";
//                String s1="tab2";
//                String s2="tab3";
//
//                switch(tabId.equals(tabId)){
//                    case s:
//                    break;
//
//                    case:
//                    break;
//
//                    case:
//                    break;
//                }
                if (tabId.equals("tab1")) {
                    pager.setCurrentItem(0, true);


                } else if (tabId.equals("tab2")) {
                    pager.setCurrentItem(1, true);
                } else if (tabId.equals("tab3")) {
                    pager.setCurrentItem(2, true);
                }
            }
        });

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //스코롤되면
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //80프로 이상 넘기면
            @Override
            public void onPageSelected(int position) {
                tabHost.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navi_drawer);

        //회색으로 아이템 처리하는거 없애는 메소드
        navigationView.setItemIconTintList(null);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                //여기다가 효과 넣으면 됨
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        //토글버튼이 액션바에 제목왼쪽에 보이도록
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //삼선 아이콘 모양으로 보이도록 토글버튼의 동기 맞추기
        drawerToggle.syncState();

        //삼선모양과 화살표아이콘이 변경되는 자동으로 변환되도록!
        drawerLayout.addDrawerListener(drawerToggle);


        //네비게이션 뷰 객체의 메뉴아이템을 클릭하는것을 듣는 리스너 객체 생성 및설정.
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_gallery:
                        Intent gal = new Intent(Intent.ACTION_PICK);
                        gal.setType(MediaStore.Images.Media.CONTENT_TYPE);
                        startActivity(gal);

                        Toast.makeText(MainActivity.this, G.login_id, Toast.LENGTH_SHORT).show();
                        //startActivity(intent);
//                        Toast.makeText(MainActivity.this, "Gallery 준비중", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_send:
                        //startActivity(intent2);
                        Intent msg = new Intent(Intent.ACTION_SEND);
//                        msg.addCategory(Intent.CATEGORY_DEFAULT);
                        msg.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.icyfruits.overapp");

//                        Toast.makeText(MainActivity.this, "공유", Toast.LENGTH_SHORT).show();
                        msg.setType("text/plain");
                        startActivity(Intent.createChooser(msg, getResources().getText(R.string.app_name)));
                        break;
                    case R.id.menu_calendar:
                        startActivity(member);
                        //Toast.makeText(MainActivity.this, "Calendar 준비중", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    public void clickHeaderIcon(View v) {
        //결과를 받기위한 목적을 갖고있는 intent
        startActivityForResult(intentProfile, 1);
        overridePendingTransition(R.anim.slider, R.anim.sliderend);
//        finish();
//       Toast.makeText(this, "Icon Click", Toast.LENGTH_SHORT).show();
//        if(image==null){
//            Toast.makeText(this, "sdfsdf", Toast.LENGTH_SHORT).show();
//        }else{
//
//            image.setImageResource(R.drawable.kei);
//        }
//        image.setBackgroundResource(R.mipmap.ic_launcher);

        //    ((ImageView)v).setImageResource(R.drawable.kei);
    }
    //액션바의 메뉴를 클릭하는 이벤트를 듣는 리스너를 통해 클릭상태를 전달

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //토글버튼이 클릭상황 인지
        drawerToggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
//        Toast.makeText(this,"aaa",Toast.LENGTH_SHORT).show();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            Toast.makeText(this,"bbb",Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawer(navidrawer);

        } else {
//            Toast.makeText(this,"ccc",Toast.LENGTH_SHORT).show();
            moveTaskToBack(true);
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onStop() {

        SharedPreferences pref = getSharedPreferences("count", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("key", G.count);
        editor.putString("id", G.login_id);
        editor.commit();
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String name = data.getStringExtra("name");
//                    String id = data.getStringExtra("id");
                    text1.setText("" + G.id);
                    text2.setText("" + name);

                    G.login_id = G.id;

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            image.setImageBitmap(bitmap);
//                showgender.setText(user.gender);
                            text1.setText(user.name);
                            text2.setText(user.email);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

            }
        }.execute();


    }
}

