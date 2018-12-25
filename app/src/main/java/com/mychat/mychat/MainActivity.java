package com.mychat.mychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class MainActivity extends AppCompatActivity {

    static final String APP_ID = "75197";
    static final String AUTH_KEY = "w25zEeQVc4BYvpM";
    static final String AUTH_SECRET = "ERECcesxLCU-w4b";
    static final String ACCOUNT_KEY = "So215sHoqwW2M9KuRDMB";

    //declare button and txtboxes
    Button signupBtn, loginBtn;
    EditText edtUser, edtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //lazmi ye method bnana prygya khudsy
        InitializeFrameWorkBnayahMene();
        //initialization of button and textboxes
        signupBtn = this.<Button>findViewById(R.id.main_edit_signupbtn);
        loginBtn = this.<Button>findViewById(R.id.main_edit_loginbtn);
        edtUser = this.<EditText>findViewById(R.id.main_edit_login);
        edtPassword = this.<EditText>findViewById(R.id.main_edit_password);


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

        //loginbtn k lye
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //take the values of edit text boxes
                final String user = edtUser.getText().toString();
                String password = edtPassword.getText().toString();

                QBUser qbUser = new QBUser(user, password);
                QBUsers.signIn(qbUser).performAsync(new QBEntityCallback<QBUser>() {
                    @Override
                    public void onSuccess(QBUser qbUser, Bundle bundle) {

                        if (edtUser.getText().toString() == "") {
                            edtUser.setError("Enter Username");
                            edtUser.requestFocus();
                        } else if (edtPassword.getText().toString() == "") {
                            edtPassword.setError("Enter Password");
                            edtPassword.requestFocus();
                        } else {
                            Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(QBResponseException e) {
                        Toast.makeText(MainActivity.this, "Enter Correct User/Password", Toast.LENGTH_SHORT).show();
                            edtUser.setText("");
                            edtPassword.setText("");
                            edtUser.requestFocus();
                    }
                });

            }
        });
    }

    private void InitializeFrameWorkBnayahMene() {
        QBSettings.getInstance().init(getApplicationContext(), APP_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
    }
}
