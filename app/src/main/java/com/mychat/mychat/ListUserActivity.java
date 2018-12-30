package com.mychat.mychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.mychat.mychat.Adapter.LisstUserAdapter;
import com.mychat.mychat.CommonPackage.CommonClass;
import com.mychat.mychat.HolderPackage.QBUserHolderClass;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.chat.utils.DialogUtils;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;

public class ListUserActivity extends AppCompatActivity {

    ListView listUser;
    Button btnCtreateChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        RetriveAllUserMethod();

        listUser = this.<ListView>findViewById(R.id.lstUsers);
        listUser.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        btnCtreateChat = this.<Button>findViewById(R.id.btn_create_chat);
        btnCtreateChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int countChoice = listUser.getCount();
                if (listUser.getCheckedItemPositions().size() == 1)
                    createPrivateChatMethod(listUser.getCheckedItemPositions());
                else if (listUser.getCheckedItemPositions().size() > 1)
                    createGroupChatMethod(listUser.getCheckedItemPositions());
                else
                    Toast.makeText(ListUserActivity.this, "Please select friend to chat", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void createGroupChatMethod(SparseBooleanArray checkedItemPositions) {
        final KProgressHUD kProgressHUD = KProgressHUD.create(ListUserActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                // .setLabel("Please wait")
                .setDetailsLabel("Please wait...")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        //yhe sy copy kr k private m add krdyngy
        int countChoice = listUser.getCount();
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < countChoice; i++) {
            if (checkedItemPositions.get(i)) {
                QBUser qbUser = (QBUser) listUser.getItemAtPosition(i);
                arrayList.add(qbUser.getId());
            }
        }

        //create chat dialog k lye
        QBChatDialog qbChatDialog = new QBChatDialog();
        qbChatDialog.setName(CommonClass.CreateChatDialogNameMethod(arrayList));
        qbChatDialog.setType(QBDialogType.GROUP);
        qbChatDialog.setOccupantsIds(arrayList);

        //fr whe kam add kia r qbChatDialog ye uper chothi line wala h
        //r yhe copy kr k private m jayga
        QBRestChatService.createChatDialog(qbChatDialog).performAsync(new QBEntityCallback<QBChatDialog>() {
            @Override
            public void onSuccess(QBChatDialog qbChatDialog, Bundle bundle) {
                kProgressHUD.dismiss(); //yha progress bar finish hojayge
                Toast.makeText(ListUserActivity.this, "Create chat successfully", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d("Error", e.getMessage());
            }
        });


    }

    //ye private chat k lye kam hoga ab
    private void createPrivateChatMethod(SparseBooleanArray checkedItemPositions) {
        final KProgressHUD kProgressHUD = KProgressHUD.create(ListUserActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                // .setLabel("Please wait")
                .setDetailsLabel("Please wait...")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        int countChoice = listUser.getCount();
      //  ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < countChoice; i++) {
            if (checkedItemPositions.get(i)) {
                QBUser qbUser = (QBUser) listUser.getItemAtPosition(i);
              //  arrayList.add(qbUser.getId());

                QBChatDialog chatDialog=DialogUtils.buildPrivateDialog(qbUser.getId());

                //fr whe kam
                QBRestChatService.createChatDialog(chatDialog).performAsync(new QBEntityCallback<QBChatDialog>() {
                    @Override
                    public void onSuccess(QBChatDialog qbChatDialog, Bundle bundle) {
                        kProgressHUD.dismiss(); //yha progress bar finish hojayge
                        Toast.makeText(ListUserActivity.this, "Create private chat successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        Log.d("Error", e.getMessage());
                    }
                });
            }
        }

    }


    private void RetriveAllUserMethod() {

        QBUsers.getUsers(null).performAsync(new QBEntityCallback<ArrayList<QBUser>>() {
            @Override
            public void onSuccess(ArrayList<QBUser> qbUsers, Bundle bundle) {

                //add cache
                QBUserHolderClass.getInstance().putUsersMethod(qbUsers);
                //create new array list to add all user from web services without current user logged
                ArrayList<QBUser> qbUserWithoutCurrent = new ArrayList<>();
                for (QBUser user : qbUsers) {
                    if (!user.getLogin().equals(QBChatService.getInstance().getUser().getLogin()))
                        qbUserWithoutCurrent.add(user);
                }
                LisstUserAdapter adapter = new LisstUserAdapter(getBaseContext(), qbUserWithoutCurrent);
                listUser.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d("Error", e.getMessage());
            }
        });
    }
}
