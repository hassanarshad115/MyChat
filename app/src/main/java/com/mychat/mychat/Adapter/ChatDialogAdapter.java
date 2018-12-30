package com.mychat.mychat.Adapter;

import android.app.Notification;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import com.mychat.mychat.R;
import com.quickblox.chat.model.QBChatDialog;

import java.util.ArrayList;

//extends krna h BaseAdapter sy fr constructor bnana h r jo jo methods ho wo bnany h
public class ChatDialogAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<QBChatDialog> qbChatDialogs;

    public ChatDialogAdapter(Context context, ArrayList<QBChatDialog> qbChatDialogs) {
        this.context = context;
        this.qbChatDialogs = qbChatDialogs;
    }

    @Override
    public int getCount() {
        return qbChatDialogs.size();
    }

    @Override
    public Object getItem(int position) {
        return qbChatDialogs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_chat_dialog_rf, null);

            TextView txtTitle, txtMessage;
            ImageView imageKlye;

            txtTitle = view.<TextView>findViewById(R.id.list_chatDialog_title);
            txtMessage = view.<TextView>findViewById(R.id.list_chatDialog_message);
            imageKlye = view.<ImageView>findViewById(R.id.image_chatDialog);

            //
            txtMessage.setText(qbChatDialogs.get(position).getLastMessage());
            txtTitle.setText(qbChatDialogs.get(position).getName());


            //Random color lyny k lye
            ColorGenerator colorGenerator = ColorGenerator.MATERIAL; 
            int randomColor = colorGenerator.getRandomColor();

            TextDrawable.IBuilder iBuilder = TextDrawable.builder().beginConfig()
                    .withBorder(4)
                    .endConfig()
                    .round();

//get first title from chat dialog for create chat image
            TextDrawable drawable = iBuilder.build(txtTitle.getText().toString().substring(0, 1).toUpperCase(), randomColor);
            imageKlye.setImageDrawable(drawable);
        }
        return view;
    }
}
