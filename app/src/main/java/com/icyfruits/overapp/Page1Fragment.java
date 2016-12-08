package com.icyfruits.overapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by m09-5 on 2016-10-18.
 */

public class Page1Fragment extends Fragment {

    ArrayList<BoardItem> items = new ArrayList<>();
    String name = null;


    ListView listView;
    BoardAdapter boardAdapter;

    Timer timer = new Timer();


    void noti() {
        NotificationManager manager
                = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        //notification 객체 생성(ALERT dIALOGUE)만드는것과 유사함
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());

        //빌더에게 알림의 여러 속성들 ( 제목, 내용, 아이콘 들을 설정할수 있다)
        builder.setSmallIcon(R.mipmap.icon2);
        //상태표시줄에 나오는 아이콘

        builder.setTicker("문자왔숑"); //상태표시줄에 잠시 보이는(보였다가 사라짐) 글씨

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.icon2);
        //상태바를 드래그 하여 아래로 내리면 보이는 알림창.(확장형 상태바)의 설정.
        //큰아이콘 설정.
        builder.setLargeIcon(bm); // 그림말고 비트맵달라고 하니까 비트맵으로 만들어서 넘겨줌/
        builder.setContentTitle("문자왔숑");
        //  builder.setContentText("Message Values");

        //진동 추가
        //진동은 디바이스에 큰 변화를 의미하므로 사용자가 미리 인지해야함. 허가받아야함.

        builder.setVibrate(new long[]{0, 500}); //대기시간 떠는 시간 0, 3000/ 0초대기후 3초진동
        //0 3000 1000 2000 =  0초대기 3초진동 1초대기 2초진동

        //사운드 추가
        //핸드폰이 갖고있는 설정되있는 것 사용.
        Uri sound = RingtoneManager.getActualDefaultRingtoneUri(getActivity(), RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(sound);


        Intent intent = new Intent(getActivity(), MainActivity.class);

        //다시 순서를 주는것
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);


        PendingIntent pending = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //알림을 확인했을때 확장상태바를 클릭했을때 다른 SecondActivity 실행.
        builder.setContentIntent(pending);


        //클릭했을때 알림확인했을때 노티피케이션을 자동 삭제.
        builder.setAutoCancel(true);

        Notification notification = builder.build();

        //알림매니저에게 알림(notify)를 요청.
        manager.notify(1, notification);
    }


    //처음 시작될때만 schedule  생성.
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timer.schedule(task, 2000, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_page2, container, false);
////////////////////////////////////////////

        listView = (ListView) view.findViewById(R.id.listview2);
        boardAdapter = new BoardAdapter(inflater, items);
        listView.setAdapter(boardAdapter);

        //getActivity().getResources() 메인 액티비티에서 데려올때/


        loadDB();

        //2000ms 후에 (1초후에_ taskl 라는 스레드를 시작해라>
        //1000ms 마다. 다시 start


        return view;
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            loadDB();
        }
    };

    void loadDB() {
        //서버에 있는 loadDB.php를 통해서 DB정보 읽어오기.
        new Thread() {
            @Override
            public void run() {

                String serverUrl = "http://devc2016.dothome.co.kr/Android/loadDB.php";

                try {
                    URL url = new URL(serverUrl);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setUseCaches(false);

                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is, "utf-8");
                    BufferedReader reader = new BufferedReader(isr);

                    StringBuffer buffer = new StringBuffer();

                    String line = reader.readLine();
                    Log.i("ec", "cc");
                    while (line != null) {
                        buffer.append(line);
                        line = reader.readLine();
                    }

                    //읽어온 데이터 문자열에서 db의 row별로 배열로 구분해서 저장.
                    String[] rows = buffer.toString().split(";");

                    if (rows.length == items.size()) return;


                    items.clear();

//                    for(int i=0; i< rows.length; i++){
//
//                    }
//

                    int num = 0;
                    //for each
                    for (String row : rows) {
                        Log.i("aa", row);
                        name = row.split("&")[0];
                        String msg = row.split("&")[1];
                        String date = row.split("&")[2];

                        //0번에 넣음으로서 밀리게되서 오름차순으로 정렬
                        items.add(0, new BoardItem(name, msg, date));


//                        if(G.check.equals("")){
//                            break;
//                        }else{
//
//                        }

                        //todo noti가 계속되는 문제가 있다

                        if (num == rows.length - 1) {
                            if (!G.check.equals(name)) {
                                if(G.count==items.size()||G.count==0)break;
                                noti();
                            }
                        }
                        num++;
                    }

                    //리스트뷰에 설정된 아답터뷰에게 데이터가 변경되었음를 통지해야만
                    //리스트뷰의 항복들이 갱신.

                    G.count = items.size();
//                    G.count = items.size();

//                    SharedPreferences pref= getActivity().getSharedPreferences("count",getActivity().MODE_PRIVATE);
//                    SharedPreferences.Editor editor =pref.edit();
//                    editor.putInt("key",G.count);
//                    editor.commit();



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            boardAdapter.notifyDataSetChanged();

                        }
                    });

                    is.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }.start();
    }

}
