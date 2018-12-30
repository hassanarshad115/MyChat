package com.mychat.mychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.QBSession;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class SignUpActivity extends AppCompatActivity {
    Button btnsignup, btncancel;
    EditText edtUser, edtPassword,fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //ye lazmi add krna h
        RegisterSessionKhudBnayaH();
        //initializw button and txtboxes
        btnsignup = this.<Button>findViewById(R.id.signup_edit_signupbtn);
        btncancel = this.<Button>findViewById(R.id.signup_edit_cancelupbtn);

        edtUser = this.<EditText>findViewById(R.id.signup_edit_username);
        edtPassword = this.<EditText>findViewById(R.id.signup_edit_password);
        fullname= this.<EditText>findViewById(R.id.signup_edit_fullname);

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close this activity
                finish();
            }
        });

        //signup
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = edtUser.getText().toString();
                String password = edtPassword.getText().toString();

                QBUser qbUser = new QBUser(username, password);
                qbUser.setFullName(fullname.getText().toString());
                
                QBUsers.signUp(qbUser).performAsync(new QBEntityCallback<QBUser>() {
                    @Override
                    public void onSuccess(QBUser qbUser, Bundle bundle) {
                        if (edtUser.getText().toString() == "") {
                            edtUser.setError("Enter Username");
                            edtUser.requestFocus();
                        } else if (edtPassword.getText().toString() == "") {
                            edtPassword.setError("Enter Password");
                            edtPassword.requestFocus();
                        } else {
                            Toast.makeText(SignUpActivity.this, "SignUp Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        //   Toast.makeText(SignUpActivity.this, "" , Toast.LENGTH_SHORT).show();
                        edtUser.setText("");
                        edtPassword.setText("");
                        edtUser.requestFocus();
                    }
                });
            }
        });


    }

    private void RegisterSessionKhudBnayaH() {
        QBAuth.createSession().performAsync(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {

            }

            @Override
            public void onError(QBResponseException e) {
                Log.d("Error", e.getMessage());
            }
        });
    }
}
