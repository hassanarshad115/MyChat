package com.mychat.mychat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quickblox.users.model.QBUser;

import java.util.ArrayList;

public class LisstUserAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<QBUser> qbUserArrayList;

    public LisstUserAdapter(Context context, ArrayList<QBUser> qbUserArrayList) {
        this.context = context;
        this.qbUserArrayList = qbUserArrayList;
    }

    @Override
    public int getCount() {
        return qbUserArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return qbUserArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if (convertView==null){
            LayoutInflater layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(android.R.layout.simple_list_item_multiple_choice,null);
            TextView textView= view.<TextView>findViewById(android.R.id.text1);
            textView.setText(qbUserArrayList.get(position).getLogin());

        }
        return view;
    }

}
