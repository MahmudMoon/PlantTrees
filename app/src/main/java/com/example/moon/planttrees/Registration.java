package com.example.moon.planttrees;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Registration extends AppCompatActivity {

    EditText _user;
    EditText _email;
    EditText _pass;
    EditText _date;
    FirebaseAuth firebaseAuth;
    ActionProcessButton _submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init_views();
        init_variables();
        init_functions();
        init_listeners();
    }

    private void init_views() {
        _user = (EditText)findViewById(R.id.et_userName);
        _email = (EditText)findViewById(R.id.et_email);
        _pass = (EditText)findViewById(R.id.et_pass);
        _submit = (ActionProcessButton) findViewById(R.id.btn_registration);
        _date = (EditText)findViewById(R.id.et_date);
    }

    private void init_variables() {
        firebaseAuth = FirebaseAuth.getInstance();
        _submit.setMode(ActionProcessButton.Mode.PROGRESS);
        _submit.setProgress(0);

        Date d = new Date();
        CharSequence s  = DateFormat.format("MMMM d, yyyy ", d.getTime());

        _date.setText(s);
    }

    private void init_functions() {

    }

    private void init_listeners() {
        _submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(_user.getText().toString())
                        && !TextUtils.isEmpty(_email.getText().toString())
                        && !TextUtils.isEmpty(_pass.getText().toString())
                        && !TextUtils.isEmpty(_date.getText().toString())
                        ) {

                    _submit.setProgress(50);
                    final String user = _user.getText().toString();
                    final String email = _email.getText().toString();
                    final String pass = _pass.getText().toString();
                    final String date = _date.getText().toString();
                    _submit.setProgress(75);
                    firebaseAuth.createUserWithEmailAndPassword(email,pass)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    _submit.setProgress(100);
                                    Toast.makeText(getApplicationContext(),"REgistrated",Toast.LENGTH_SHORT).show();
                                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                    DatabaseReference databaseReference = firebaseDatabase.getReference(Info.user);
                                    String key = databaseReference.push().getKey();
                                    CreateUser createUser = new CreateUser(email,pass,user,date,0,key);
                                    Task<Void> voidTask = databaseReference.child(key).setValue(createUser);
                                    // check for correctly stored data
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            _submit.setProgress(-1);
                            Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                            //finish();
                        }
                    });

                }else{
                    Toast.makeText(getApplicationContext(),"Field Empty",Toast.LENGTH_SHORT).show();
                    _submit.setProgress(-1);
                }
            }
        });
    }
}
