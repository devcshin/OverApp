











//////////

package com.icyfruits.overapp;

        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.design.widget.NavigationView;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.io.BufferedReader;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.OutputStream;
        import java.net.HttpURLConnection;
        import java.net.URL;

/**
 * Created by m09-5 on 2016-10-18.
 */

public class Page2Fragment extends Fragment {

    Button btn;
    EditText msg1;
    String server = "http://devc2016.dothome.co.kr/Android/insertDB.php";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page1, container, false);

        /////////////////////////////////
//        navidrawer = (NavigationView) view.findViewById(R.id.navi_drawer);
//        View header = navidrawer.getHeaderView(0);
////// TODO: 2016-11-03
//        text1=(TextView)header.findViewById(R.id.text1);
//        text2=(TextView)header.findViewById(R.id.text2);

/////////////////////////////////////////////////////////

        msg1 = (EditText) view.findViewById(R.id.msg_edit);
        btn = (Button) view.findViewById(R.id.save1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread() {
                    @Override
                    public void run() {

//                        name = name1.getText().toString();
                        String message = msg1.getText().toString();


                        try {
                            URL url = new URL(server);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("POST");
                            connection.setDoOutput(true);
                            connection.setDoInput(true);
                            connection.setUseCaches(false);

                            String data = "name=" + G.login_id + "&message=" + message;

                            G.check=G.login_id;

                            OutputStream os = connection.getOutputStream();
                            os.write(data.getBytes());
                            os.flush();
                            os.close();

                            //서버로부터 에코를 받아옴. 읽어오기.
                            InputStream is = connection.getInputStream();
                            InputStreamReader isr = new InputStreamReader(is);
                            BufferedReader reader = new BufferedReader(isr);

                            final StringBuffer buffer = new StringBuffer();

                            String line = reader.readLine();
                            while (line != null) {
                                buffer.append(line + "\n");
                                line = reader.readLine();
                            }

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                            new AlertDialog.Builder(getApplicationContext())
//                                    .setMessage(buffer.toString())
//                                    .setPositiveButton("OK",null).create().show();


//                                    name1.setText("");
                                    msg1.setText("");

                                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
        });


        return view;
    }
}






