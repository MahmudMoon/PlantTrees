package com.example.moon.planttrees;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static android.support.constraint.Constraints.TAG;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    Button btn_registration;
    ActionProcessButton login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        btn_registration = (Button)findViewById(R.id.registration);

        login = (ActionProcessButton) findViewById(R.id.email_sign_in_button);
        login.setMode(ActionProcessButton.Mode.PROGRESS);
        login.setProgress(0);









        btn_registration.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,Registration.class);
                startActivity(intent);
            }
        });



        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();

            }
        });

    }







    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        login.setProgress(50);
        boolean isOk = true;
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        final String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();



        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
           isOk =false;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));

        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));

        }else{
            Log.i("MyTag", "attemptLogin: "+"okk");
            if(isOk){
                login.setProgress(75);
                Log.i("MyTag", "attemptLogin: "+"Am i OK");
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                login.setProgress(100);
                                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                                Log.i("MyTag", "onSuccess: "+"okk");

                                Intent intent = new Intent(LoginActivity.this,Instruction.class);
                                Info.setEmail(email);
                                startActivity(intent);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        login.setProgress(-1);

                        Log.i("MyTag", "onSuccess: "+"Not okk");
                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }


    }

    private boolean isEmailValid(String email) {
        if(email.contains("@")&&email.contains(".com")){
            return true;
        }else{
            return false;
        }
    }

    private boolean isPasswordValid(String password) {
        if(password.length()>=6){
            return true;
        }else{
            return false;
        }
    }



}

