package com.mychat.mychat;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.mychat.mychat.Adapter.ChatDialogAdapter;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.BaseService;
import com.quickblox.auth.session.QBSession;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.BaseServiceException;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;

public class ChatDialogActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    ListView listView;


    @Override
    protected void onResume() {
        super.onResume();
        LoadChatDialogMethod();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_dialog);

        CreateSessionMethod();

        //initialize lazmi krna hota h
        listView = this.<ListView>findViewById(R.id.lstChatDialog);

        //load all chat of the user's
        LoadChatDialogMethod();



        //floating Action Button
        floatingActionButton= this.<FloatingActionButton>findViewById(R.id.chatDialogAddUser);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatDialogActivity.this,ListUserActivity.class));
            }
        });
    }


    private void LoadChatDialogMethod() {
        QBRequestGetBuilder qbRequestGetBuilder = new QBRequestGetBuilder();
        qbRequestGetBuilder.setLimit(100);

        QBRestChatService.getChatDialogs(null, qbRequestGetBuilder).performAsync(new QBEntityCallback<ArrayList<QBChatDialog>>() {
            @Override
            public void onSuccess(ArrayList<QBChatDialog> qbChatDialogs, Bundle bundle) {

                ChatDialogAdapter adapter = new ChatDialogAdapter(getBaseContext(), qbChatDialogs);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onError(QBResponseException e) {
                Log.d("Error", e.getMessage());
            }
        });
    }

    KProgressHUD kProgressHUD;
    private void CreateSessionMethod() {

          kProgressHUD = KProgressHUD.create(ChatDialogActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                // .setLabel("Please wait")
                .setDetailsLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        String user, password;
        user = getIntent().getStringExtra("user");
        password = getIntent().getStringExtra("password");

        final QBUser qbUser = new QBUser(user, password);
        QBAuth.createSession(qbUser).performAsync(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {

                qbUser.setId(qbSession.getUserId());
                try {
                    qbUser.setPassword(BaseService.getBaseService().getToken());
                } catch (BaseServiceException e) {
                    e.printStackTrace();
                }

                //ye b phly walo jesy bnana h
                QBChatService.getInstance().login(qbUser, new QBEntityCallback() {
                    @Override
                    public void onSuccess(Object o, Bundle bundle) {
                        kProgressHUD.dismiss();
                       Toast.makeText(ChatDialogActivity.this, "dismis hoja", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(QBResponseException e) {

                        Log.d("Error ye h", e.getMessage());
                    }
                });
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d("Error ye h", e.getMessage());
            }
        });


    }
}
