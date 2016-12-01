package com.icyfruits.overapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by m09-5 on 2016-10-31.
 */

public class BoardAdapter extends BaseAdapter {
    //멤버변수
    LayoutInflater inflater;
    ArrayList<BoardItem> items;

    public BoardAdapter(LayoutInflater inflater, ArrayList<BoardItem> items) {
        this.inflater = inflater;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView=inflater.inflate(R.layout.list_item, parent, false);
        }

        BoardItem item=items.get(position);

        TextView text_name=(TextView)convertView.findViewById(R.id.text_name);
        TextView text_msg=(TextView)convertView.findViewById(R.id.text_msg);
        TextView text_date=(TextView)convertView.findViewById(R.id.text_date);

        text_name.setText(item.getName());
        text_msg.setText(item.getMessage());
        text_date.setText(item.getDate());

        return convertView;
    }
}
